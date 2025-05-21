package com.kadirkara.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kadirkara.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	@Query("SELECT r FROM User r WHERE r.customer.id = :customerId")
	Optional<User> findByCustomerId(@Param("customerId") Long customerId);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.lastLoginTime = :lastLoginTime WHERE u.id = :userId")
	void updateLastLoginTime(@Param("userId") Long userId, @Param("lastLoginTime") Date lastLoginTime);
	
	
}
