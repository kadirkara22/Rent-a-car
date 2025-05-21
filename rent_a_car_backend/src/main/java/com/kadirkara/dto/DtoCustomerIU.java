package com.kadirkara.dto;

import java.util.Date;

import com.kadirkara.model.Account;
import com.kadirkara.model.Address;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoCustomerIU {

	@NotNull
    private String firstName;
	@NotNull
    private String lastName;
	@NotNull
    private String phoneNumber;
	@NotNull
    private Date registrationDate;
	@NotNull
    private Date dateOfBirth;
	@NotNull
	private String tckn;
	@NotNull
    private String licenseType;
	@NotNull
    private Date licenseIssueDate;
	@NotNull
    private Account account;
	@NotNull
    private Address address;
}
