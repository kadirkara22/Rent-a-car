package com.kadirkara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkara.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
