package com.kadirkara.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoCustomerIU;
import com.kadirkara.dto.DtoUser;
import com.kadirkara.exception.BaseException;
import com.kadirkara.exception.ErrorMessage;
import com.kadirkara.model.Address;
import com.kadirkara.model.Customer;
import com.kadirkara.model.User;
import com.kadirkara.repository.CustomerRepository;
import com.kadirkara.repository.UserRepository;

@Service
public class UserService {
	  private final BCryptPasswordEncoder bCryptPasswordEncoder;
	  private final UserRepository userRepository;
	  public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserRepository userRepository) {
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	        this.userRepository=userRepository;
	    }
	  public String extractUsernameFromEmail(String email) {
	        if (email == null || !email.contains("@")) {
	            throw new IllegalArgumentException("Geçersiz email adresi: " + email);
	        }
	        return email.substring(0, email.indexOf("@"));
	    }

	    public User createUser(AuthRequest request,Customer customer) {
	        
	        String username = extractUsernameFromEmail(request.getEmail());
	        
	        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
	       
	        User user = new User();
	        user.setCreateTime(new Date());
	        //user.setPassword(request.getPassword());
	        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
	        user.setUsername(username);
            user.setCustomer(customer);
            user.setLastLoginTime(new Date());
	         customer.setUser(user);
	        return user;
	    }
	    
	    public DtoUser saveUser(AuthRequest request,Customer customer) {
	    	DtoUser dtoUser=new DtoUser();
	    	User savedUser=userRepository.save(createUser(request,customer));
	    	
	    	BeanUtils.copyProperties(savedUser, dtoUser);
	    	
	    	return dtoUser;
	    }
}