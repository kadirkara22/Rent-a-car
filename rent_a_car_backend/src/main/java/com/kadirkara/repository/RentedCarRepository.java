package com.kadirkara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kadirkara.model.RentedCar;
@Repository
public interface RentedCarRepository extends JpaRepository<RentedCar, Long>{
	
	@Query("SELECT r FROM RentedCar r WHERE r.customer.id = :customerId")
	List<RentedCar> findByCustomerId(@Param("customerId") Long customerId);


}
