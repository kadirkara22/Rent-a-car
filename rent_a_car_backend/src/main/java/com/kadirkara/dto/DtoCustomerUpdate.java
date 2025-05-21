package com.kadirkara.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoCustomerUpdate {
	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private Date dateOfBirth;
	    private String licenseType;
	    private Date licenseIssueDate;
	    private String tckn;
}
