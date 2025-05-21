package com.kadirkara.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoCarIU;
import com.kadirkara.enums.CarStatusType;
import com.kadirkara.model.Car;
import com.kadirkara.repository.CarRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {

	private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
   
    
    public DtoCar addCar(DtoCarIU dtoCarIU) {
    	Car car =new Car();
    	DtoCar dtoCar = new DtoCar();
    	
    	car.setCreateTime(new Date());
    	BeanUtils.copyProperties(dtoCarIU, car);
        Car savedCar=carRepository.save(car);
        
        BeanUtils.copyProperties(savedCar, dtoCar);
        
        return dtoCar;
    }

    
    public List<DtoCar> getAllCars() {
    	List<DtoCar> dtoList=new ArrayList<>();
    	List<Car> carList=carRepository.findAll();
    	
    	for(Car car:carList) {
    		DtoCar dto=new DtoCar();
			BeanUtils.copyProperties(car, dto);
			dtoList.add(dto);
		}
		return dtoList;

		
    }
    
   
    public DtoCar getCarById(Long id) {
    	DtoCar dtoCar=new DtoCar();
    	Optional<Car> optional = carRepository.findById(id);
    	if(optional.isEmpty()) {
    		throw new EntityNotFoundException("Araç bulunamadı: " + id);
		}
    	Car dbCar = optional.get();
    	BeanUtils.copyProperties(dbCar, dtoCar);
    	return dtoCar;
       
    }

    public DtoCar updateCar(Long id, DtoCarIU dtoCarIU) {
    	DtoCar dtoCar=new DtoCar();
    	
      Optional<Car> optional= carRepository.findById(id);
      
      if (optional.isEmpty()) {
          throw new EntityNotFoundException("Araç bulunamadı: " + id);
      }
      
        Car dbcar=optional.get();
    	  BeanUtils.copyProperties(dtoCarIU, dbcar);
    	  
    	  Car updatedCar=carRepository.save(dbcar);
    	  BeanUtils.copyProperties(updatedCar, dtoCar);
    	  
    	  return dtoCar;
      
 
    }

    
    public void deleteCar(Long id) {
       Optional<Car> optional = carRepository.findById(id);
       if(optional.isPresent()) {
    	   carRepository.delete(optional.get());
    	   
       }
    }

    
    public DtoCar updateCarStatus(Long id, CarStatusType status) {
    	DtoCar dtoCar=new DtoCar();
        Optional<Car> optional = carRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Araç bulunamadı: " + id);
        }
       Car dbCar= optional.get();
        dbCar.setCarStatusType(status);
        Car updatedCar=carRepository.save(dbCar);
        BeanUtils.copyProperties(updatedCar, dtoCar);
        return dtoCar;
    }
/*
    // Fiyata göre araçları filtrele
    public List<Car> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findByOfficePriceBetween(minPrice, maxPrice);
    }*/
   
}
