package com.kadirkara.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.DtoAccount;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.dto.DtoPickupLocation;
import com.kadirkara.dto.DtoRentedCar;
import com.kadirkara.dto.DtoRentedCarIU;
import com.kadirkara.enums.CarStatusType;
import com.kadirkara.exception.BaseException;
import com.kadirkara.exception.ErrorMessage;
import com.kadirkara.exception.MessageType;
import com.kadirkara.model.Account;
import com.kadirkara.model.Car;
import com.kadirkara.model.Customer;
import com.kadirkara.model.PickupLocation;
import com.kadirkara.model.PickupLocationCar;
import com.kadirkara.model.RentedCar;
import com.kadirkara.repository.AccountRepository;
import com.kadirkara.repository.CarRepository;
import com.kadirkara.repository.CustomerRepository;
import com.kadirkara.repository.PickupLocationCarRepository;
import com.kadirkara.repository.PickupLocationRepository;
import com.kadirkara.repository.RentedCarRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
public class RentedCarService {

	    private final PickupLocationRepository pickupLocationRepository;
	    private final CustomerRepository customerRepository;
	    private final CarRepository carRepository;
	    private final RentedCarRepository rentedCarRepository;
	    private final AccountRepository accountRepository;
	    private final PickupLocationCarRepository pickupLocationCarRepository;

    public RentedCarService(RentedCarRepository rentedCarRepository,
    		PickupLocationRepository pickupLocationRepository,
    		CarRepository carRepository,CustomerRepository customerRepository,
    		AccountRepository accountRepository,
    		PickupLocationCarRepository pickupLocationCarRepository) {
        this.rentedCarRepository = rentedCarRepository;
        this.pickupLocationRepository=pickupLocationRepository;
        this.carRepository = carRepository;
        this.customerRepository=customerRepository;
        this.accountRepository=accountRepository;
        this.pickupLocationCarRepository=pickupLocationCarRepository;
    }
    
    public DtoRentedCar rentCar(DtoRentedCarIU dtoRentedCarIU) {
    	System.out.println("PickupLocation ID: " + dtoRentedCarIU.getPickupLocation().getId());
        System.out.println("Car ID: " + dtoRentedCarIU.getCarId());
        PickupLocation pickupLocation = pickupLocationRepository.findById(dtoRentedCarIU.getPickupLocation().getId())
                .orElseThrow(() -> new EntityNotFoundException("location bulunamadı: " + dtoRentedCarIU.getPickupLocation().getId()));

        
        Car car = carRepository.findById(dtoRentedCarIU.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + dtoRentedCarIU.getCarId()));

        
        Customer customer = customerRepository.findById(dtoRentedCarIU.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı: " + dtoRentedCarIU.getCustomerId()));
        

        PickupLocationCar pickupLocationCar = pickupLocationCarRepository.findByPickupLocationAndCarId(pickupLocation, dtoRentedCarIU.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı veya kiralama için uygun değil."));

        String carStatus = pickupLocationCar.getStatus(); 
        /*if (!"ACTIVE".equals(carStatus)) {
            throw new IllegalStateException("Araç şu anda uygun değil. Durum: " + carStatus);
        }
        */
        Account account = customer.getAccount();
        if (account == null) {
            throw new IllegalStateException("Müşterinin bir hesap kaydı bulunamadı.");
        }

        
        long rentalDays = ChronoUnit.DAYS.between(dtoRentedCarIU.getStartDate(), dtoRentedCarIU.getEndDate());
        BigDecimal rentalCost = car.getOnlinePrice().multiply(BigDecimal.valueOf(rentalDays));

        
        if (account.getBalance().compareTo(rentalCost) < 0) {
           throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH, "")); 
        }
          
       
        account.setBalance(account.getBalance().subtract(rentalCost));
        account.setLastUpdateTime(new Date());
        accountRepository.save(account);

       
        RentedCar rentedCar = new RentedCar();
        rentedCar.setPickupLocation(pickupLocation);
        rentedCar.setCar(car);
        rentedCar.setCustomer(customer);
        rentedCar.setCreateTime(new Date());
        rentedCar.setStartDate(dtoRentedCarIU.getStartDate());
        rentedCar.setEndDate(dtoRentedCarIU.getEndDate());

        pickupLocationCar.setStatus("RESERVED");
        car.setCarStatusType(CarStatusType.RESERVED);
        car.setStartDate(dtoRentedCarIU.getStartDate());
        car.setEndDate(dtoRentedCarIU.getEndDate());
        pickupLocationCar.setLastUpdateTime(new Date());
        rentedCar = rentedCarRepository.save(rentedCar);

      
        return convertToDto(rentedCar);
    }

    private DtoRentedCar convertToDto(RentedCar rentedCar) {
        DtoRentedCar dtoRentedCar = new DtoRentedCar();
        DtoCustomer dtoCustomer = new DtoCustomer();
        DtoPickupLocation dtoPickupLocation=new DtoPickupLocation();
        DtoCar dtoCar=new DtoCar();
        
        BeanUtils.copyProperties(rentedCar, dtoRentedCar);
        BeanUtils.copyProperties(rentedCar.getCustomer(), dtoCustomer);
        BeanUtils.copyProperties(rentedCar.getPickupLocation(), dtoPickupLocation);
        BeanUtils.copyProperties(rentedCar.getCar(), dtoCar);
        
        dtoRentedCar.setCustomer(dtoCustomer);
        dtoRentedCar.setPickupLocation(dtoPickupLocation);
        dtoRentedCar.setCar(dtoCar);
        dtoRentedCar.setStartDate(rentedCar.getStartDate());
        dtoRentedCar.setEndDate(rentedCar.getEndDate());
        return dtoRentedCar;
    }
    
    

   
    public List<DtoRentedCar> getRentedCarsByCustomer(Long id) {
       List<DtoRentedCar> dtoRentedCarList=new ArrayList<>();
    	List<RentedCar>  rentedCarList= rentedCarRepository.findByCustomerId(id); 
    
             for(RentedCar car:rentedCarList) {                
    	         		DtoRentedCar dto=new DtoRentedCar(); 
    	         		DtoCar dtoCar=new DtoCar();
    	         		DtoCustomer dtoCustomer=new DtoCustomer();
    	         		DtoPickupLocation dtoPickupLocation=new DtoPickupLocation();
    	         		  DtoAddress dtoAddress = new DtoAddress();
    	         			DtoAccount dtoAccount = new DtoAccount();
    	         		BeanUtils.copyProperties(car.getCar(), dtoCar);
    	         		BeanUtils.copyProperties(car.getCustomer(),dtoCustomer);
    	         		BeanUtils.copyProperties(car.getPickupLocation(), dtoPickupLocation);
    	         		BeanUtils.copyProperties(car.getCustomer().getAccount(), dtoAccount);
    	         		BeanUtils.copyProperties(car.getCustomer().getAddress(), dtoAddress);
    	         		dto.setCar(dtoCar);
    	         		dtoCustomer.setAccount(dtoAccount);
    	         		dtoCustomer.setAddress(dtoAddress);
    	         		dto.setCustomer(dtoCustomer);
    	         		dto.setPickupLocation(dtoPickupLocation);
    	         		BeanUtils.copyProperties(car, dto);
    	     			dtoRentedCarList.add(dto);                  
    	     		}                                   
    	     	                     
    
    	return dtoRentedCarList;
    }
    
    public List<DtoRentedCar> getAllRents() {
    	List<DtoRentedCar> dtoList=new ArrayList<>();
    	List<RentedCar> rentList=rentedCarRepository.findAll();
    	
    	for(RentedCar rent:rentList) {
    		DtoRentedCar dto=new DtoRentedCar();
    		DtoCar dtoCar=new DtoCar();
     		DtoCustomer dtoCustomer=new DtoCustomer();
     		DtoPickupLocation dtoPickupLocation=new DtoPickupLocation();
     		DtoAddress dtoAddress = new DtoAddress();
 			DtoAccount dtoAccount = new DtoAccount();
 			BeanUtils.copyProperties(rent.getCar(), dtoCar);
     		BeanUtils.copyProperties(rent.getCustomer(),dtoCustomer);
     		BeanUtils.copyProperties(rent.getPickupLocation(), dtoPickupLocation);
     		BeanUtils.copyProperties(rent.getCustomer().getAccount(), dtoAccount);
     		BeanUtils.copyProperties(rent.getCustomer().getAddress(), dtoAddress);
     		dtoCustomer.setAccount(dtoAccount);
     		dtoCustomer.setAddress(dtoAddress);
     		dto.setCustomer(dtoCustomer);
     		dto.setCar(dtoCar);
     		dto.setPickupLocation(dtoPickupLocation);
			BeanUtils.copyProperties(rent, dto);
			dtoList.add(dto);
		}
		return dtoList;

		
    }
    
}

