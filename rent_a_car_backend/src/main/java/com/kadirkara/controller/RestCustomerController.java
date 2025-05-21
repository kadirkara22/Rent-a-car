package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.DtoAccountIU;
import com.kadirkara.dto.DtoAddressIU;
import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.dto.DtoCustomerIU;
import com.kadirkara.dto.DtoCustomerUpdate;
import com.kadirkara.service.CustomerService;

@RestController
@RequestMapping("/api/customer/hesabim")
public class RestCustomerController {
	private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

 @GetMapping(path = "/bakiye/{id}")
    public ResponseEntity<DtoCustomer> getAccount(@PathVariable(name="id") Long customer_id) {
       DtoCustomer dtoCustomer=customerService.getAccountByCustomer(customer_id);
        return ResponseEntity.ok(dtoCustomer);
    }
 
    
    @PatchMapping("/{customerId}/update-account")
    public ResponseEntity<DtoCustomer> updateAccount(
            @PathVariable Long customerId, 
            @RequestBody DtoAccountIU dtoAccountIU) {
        DtoCustomer updatedCustomer = customerService.updateAccount(customerId, dtoAccountIU);
        return ResponseEntity.ok(updatedCustomer);
    }

    @PatchMapping("/{customerId}/update-address")
    public ResponseEntity<DtoCustomer> updateAddress(
            @PathVariable Long customerId, 
            @RequestBody DtoAddressIU dtoAddressIU) {
        DtoCustomer updatedCustomer = customerService.updateAddress(customerId, dtoAddressIU);
        return ResponseEntity.ok(updatedCustomer);
    }
    
    @PatchMapping("/{customerId}/update")
    public ResponseEntity<DtoCustomer> updateCustomerDetails(
            @PathVariable Long customerId, 
            @RequestBody DtoCustomerIU dtoCustomerIU) {
    	DtoCustomer updatedCustomer = customerService.updateCustomerDetails(customerId, dtoCustomerIU);
        return ResponseEntity.ok(updatedCustomer);
    }
    

   
}
