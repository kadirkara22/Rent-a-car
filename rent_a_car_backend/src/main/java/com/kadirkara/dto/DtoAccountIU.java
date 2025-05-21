package com.kadirkara.dto;

import java.math.BigDecimal;
import java.util.List;

import com.kadirkara.enums.CurrencyType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAccountIU {
	@NotNull
	private BigDecimal balance;
	@NotNull
	private String accountNo;

	
	@NotNull
	private String iban;
	
	private CurrencyType currencyType; 
	@NotNull
	private List<DtoTransaction> transactions; 
}
