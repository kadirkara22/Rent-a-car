package com.kadirkara.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.DtoAccount;
import com.kadirkara.dto.DtoAccountIU;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoAddressIU;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.dto.DtoCustomerIU;
import com.kadirkara.dto.DtoCustomerUpdate;
import com.kadirkara.dto.DtoUser;
import com.kadirkara.model.Account;
import com.kadirkara.model.Address;
import com.kadirkara.model.Customer;
import com.kadirkara.model.User;
import com.kadirkara.repository.CustomerRepository;
import com.kadirkara.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {
	 

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final AccountService accountService;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository,UserService userService,
    		AccountService accountService,AddressService addressService,
    		UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userService=userService;
        this.addressService = addressService;
        this.accountService=accountService;
        this.userRepository=userRepository;
    }

    public Customer createCustomer(AuthRequest request) {
       DtoCustomer dtoCustomer=new DtoCustomer();
        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        BeanUtils.copyProperties(request.getCustomer(), customer);
        customer.setEmail(request.getEmail());
        customer.setLastLoginTime(new Date());
        
        
        DtoAddress dtoAddress = addressService.saveAddress(request,customer);
        

        DtoAccount dtoAccount = accountService.saveAccount(request,customer);
        
        DtoUser dtoUser = userService.saveUser(request,customer);
 
        
        
        return customer;
    }
	public DtoCustomer saveCustomer(AuthRequest request) {
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();
		
		Customer savedCustomer = customerRepository.save(createCustomer(request));
		
		BeanUtils.copyProperties(savedCustomer, dtoCustomer);
		BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);	
		
		dtoCustomer.setAddress(dtoAddress);
		dtoCustomer.setAccount(dtoAccount);
		return dtoCustomer;
	}
	  
	public DtoCustomer getAccountByCustomer(Long customerId) {
		DtoCustomer dtoCustomer=new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
			DtoAccount dtoAccount = new DtoAccount();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı ID: " + customerId));
		BeanUtils.copyProperties(customer, dtoCustomer);
		BeanUtils.copyProperties(customer.getAccount(), dtoAccount);
		BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
		dtoCustomer.setAccount(dtoAccount);
		dtoCustomer.setAddress(dtoAddress);
		return dtoCustomer;
	}

    public DtoCustomer updateAccount(Long customerId, DtoAccountIU dtoAccountIU) {
       DtoCustomer dtoCustomer=new DtoCustomer();
       DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı ID: " + customerId));

        
        Account account = customer.getAccount();
        if (account == null) {
            throw new IllegalArgumentException("Bu müşteriye ait hesap mevcut değil.");
        }

        BigDecimal newBalance = null;
        if (dtoAccountIU.getBalance() != null) {
            newBalance = account.getBalance().add(dtoAccountIU.getBalance());
        }
        BeanUtils.copyProperties(dtoAccountIU, account);
        
        if (newBalance != null) {
            account.setBalance(newBalance);
        }
        account.setLastUpdateTime(new Date());
        Customer savedCustomer = customerRepository.save(customer);
        
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);
        BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
        BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);
        dtoCustomer.setAddress(dtoAddress);
        dtoCustomer.setAccount(dtoAccount);

        return dtoCustomer;
    }

    public DtoCustomer updateAddress(Long customerId, DtoAddressIU dtoAddressIU) {
    	 DtoCustomer dtoCustomer=new DtoCustomer();
    	 DtoAddress dtoAddress = new DtoAddress();
 		DtoAccount dtoAccount = new DtoAccount();
    
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        
        Address address = customer.getAddress();
        if (address == null) {
            throw new IllegalArgumentException("Address does not exist for this customer.");
        }

       BeanUtils.copyProperties(dtoAddressIU, address);
        address.setLastUpdateTime(new Date());

        Customer savedCustomer = customerRepository.save(customer); 
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);
        BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
        BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);
        dtoCustomer.setAddress(dtoAddress);
        dtoCustomer.setAccount(dtoAccount);
       
        return dtoCustomer;
    }
    
    public DtoCustomer updateCustomerDetails(Long customerId, DtoCustomerIU dtoCustomerIU) {
    	 DtoCustomer dtoCustomer=new DtoCustomer();

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        BeanUtils.copyProperties(dtoCustomerIU, customer);
        customer.setLastUpdateTime(new Date());
        Customer savedCustomer = customerRepository.save(customer);
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);

        return dtoCustomer;
    }



    
}
