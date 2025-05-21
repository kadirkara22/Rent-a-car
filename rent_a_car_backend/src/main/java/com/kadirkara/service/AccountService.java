package com.kadirkara.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.DtoAccount;
import com.kadirkara.dto.DtoAccountIU;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoCustomerIU;
import com.kadirkara.enums.CurrencyType;
import com.kadirkara.model.Account;
import com.kadirkara.model.Address;
import com.kadirkara.model.Customer;
import com.kadirkara.model.Transaction;
import com.kadirkara.repository.AccountRepository;
import com.kadirkara.repository.AddressRepository;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

	 public Account createAccount(AuthRequest request,Customer customer) {
	        
	        Account account = new Account();
	        
	       
	        BeanUtils.copyProperties(request.getCustomer().getAccount(), account);
	        account.setCreateTime(new Date());
	        //account.setCustomer(customer); 
	        customer.setAccount(account);
	        return account;
	    }
	 
	  public DtoAccount saveAccount(AuthRequest request,Customer customer) {
	    	DtoAccount dtoAccount=new DtoAccount();
	    	Account savedAccount=accountRepository.save(createAccount(request,customer));
	    	
	    	BeanUtils.copyProperties(savedAccount, dtoAccount);
	    	
	    	return dtoAccount;
	    }
}
