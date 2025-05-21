package com.kadirkara.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.kadirkara.enums.CurrencyType;
import com.kadirkara.model.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAccount extends DtoBase {
   
	    private BigDecimal balance;
	    
	    private String accountNo;
	    
	    private String iban;
	    
	    private CurrencyType currencyType; 
	    
	    //private List<DtoTransaction> transactions; 
	    	   
}
