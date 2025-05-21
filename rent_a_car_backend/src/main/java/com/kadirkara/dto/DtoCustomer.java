package com.kadirkara.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomer extends DtoBase {

    private String firstName;
    
    private String lastName;
    
    private String phoneNumber;
    
    private Date registrationDate;
    
    private Date dateOfBirth;
    
    private String licenseType;
    
    private String email;
    
    private String tckn;
    
    private Date licenseIssueDate;
    
    private Date lastLoginTime;
    
    private DtoAccount account; 
    
    private DtoAddress address; 
    
}
