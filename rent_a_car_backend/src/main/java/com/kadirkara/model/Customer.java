package com.kadirkara.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "license_issue_date")
    private Date licenseIssueDate;
    
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    
    @Column(name = "tckn")
    private String tckn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "user_id" ,nullable = true)
    private User user;
    
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
}
