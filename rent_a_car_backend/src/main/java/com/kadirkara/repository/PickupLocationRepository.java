package com.kadirkara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkara.model.PickupLocation;
@Repository
public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long>{

}
