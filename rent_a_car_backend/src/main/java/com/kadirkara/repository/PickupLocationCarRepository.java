package com.kadirkara.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkara.model.Car;
import com.kadirkara.model.PickupLocation;
import com.kadirkara.model.PickupLocationCar;
@Repository
public interface PickupLocationCarRepository extends JpaRepository<PickupLocationCar, Long>{

	 boolean existsByPickupLocationAndCar(PickupLocation pickupLocation, Car car);
	 boolean existsByCar(Car car);
	 
	 List<PickupLocationCar> findAllByPickupLocation(PickupLocation pickupLocation);

	    Optional<PickupLocationCar> findByPickupLocationAndCarId(PickupLocation pickupLocation, Long carId);
}
