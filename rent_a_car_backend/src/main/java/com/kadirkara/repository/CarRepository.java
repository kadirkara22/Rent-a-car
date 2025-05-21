package com.kadirkara.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkara.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
 
	 List<Car> findByOfficePriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
