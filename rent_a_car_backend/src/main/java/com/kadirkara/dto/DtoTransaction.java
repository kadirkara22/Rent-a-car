package com.kadirkara.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.kadirkara.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTransaction extends DtoBase{
	    
	    private BigDecimal amount;
	    
	    private TransactionType transactionType; 
	    
	    private Date transactionDate;
	    
	    private Long accountId; 
}
