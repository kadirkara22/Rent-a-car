package com.kadirkara.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.model.Car;
import com.kadirkara.model.PickupLocation;
import com.kadirkara.model.PickupLocationCar;
import com.kadirkara.repository.CarRepository;
import com.kadirkara.repository.PickupLocationCarRepository;
import com.kadirkara.repository.PickupLocationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PickupLocationCarService {

    private final PickupLocationRepository pickupLocationRepository;
    private final CarRepository carRepository;
    private final PickupLocationCarRepository pickupLocationCarRepository;

    public PickupLocationCarService(PickupLocationRepository pickupLocationRepository,
                                    CarRepository carRepository,
                                    PickupLocationCarRepository pickupLocationCarRepository) {
        this.pickupLocationRepository = pickupLocationRepository;
        this.carRepository = carRepository;
        this.pickupLocationCarRepository = pickupLocationCarRepository;
    }

    @Transactional
    public void assignCarToLocation(Long locationId, Long carId, String status) {
        
        PickupLocation location = pickupLocationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));

      
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));

        
        boolean carAlreadyAssigned = pickupLocationCarRepository.existsByCar(car);
        if (carAlreadyAssigned) {
            throw new IllegalArgumentException("This car is already assigned to another location.");
        }

        
        PickupLocationCar pickupLocationCar = new PickupLocationCar();
        pickupLocationCar.setPickupLocation(location);
        pickupLocationCar.setCar(car);
        pickupLocationCar.setStatus(status);
        pickupLocationCar.setCreateTime(new Date());

       
        pickupLocationCarRepository.save(pickupLocationCar);
    }
    
   
    public List<DtoCar> getCarsByLocation(Long locationId) {
        PickupLocation location = pickupLocationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));

        return pickupLocationCarRepository.findAllByPickupLocation(location)
                .stream()
                .map(pickupLocationCar -> mapToDtoCar(pickupLocationCar.getCar()))
                .collect(Collectors.toList());
    }
    private DtoCar mapToDtoCar(Car car) {
        DtoCar dtoCar = new DtoCar();
       BeanUtils.copyProperties(car, dtoCar);
        return dtoCar;
    }



        
    public DtoCar getCarByLocationAndCarId(Long locationId, Long carId) {
        PickupLocation location = pickupLocationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));
        
        PickupLocationCar pickupLocationCar = pickupLocationCarRepository.findByPickupLocationAndCarId(location, carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found in this location."));
        
        return mapToDtoCar(pickupLocationCar.getCar());
    }


        
        public void updateCarStatus(Long locationId, Long carId, String status) {
            PickupLocation location = pickupLocationRepository.findById(locationId)
                    .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));
            PickupLocationCar pickupLocationCar = pickupLocationCarRepository.findByPickupLocationAndCarId(location, carId)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found in this location."));
            pickupLocationCar.setStatus(status);
            pickupLocationCarRepository.save(pickupLocationCar);
        }

        
        public void removeCarFromLocation(Long locationId, Long carId) {
            PickupLocation location = pickupLocationRepository.findById(locationId)
                    .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));
            PickupLocationCar pickupLocationCar = pickupLocationCarRepository.findByPickupLocationAndCarId(location, carId)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found in this location."));
            pickupLocationCarRepository.delete(pickupLocationCar);
        }
    

    

}
