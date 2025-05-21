package com.kadirkara.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kadirkara.model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	
	@Modifying
	@Transactional
	@Query("UPDATE Customer c SET c.lastLoginTime = :lastLoginTime WHERE c.user.id = :userId")
	void updateLastLoginTime(@Param("userId") Long userId, @Param("lastLoginTime") Date lastLoginTime);
}
