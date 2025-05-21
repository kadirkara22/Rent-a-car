package com.kadirkara.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoAddressIU;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.model.Address;
import com.kadirkara.model.Customer;
import com.kadirkara.repository.AddressRepository;

@Service
public class AddressService {


    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(AuthRequest request,Customer customer) {
        
        Address address = new Address();
        DtoAddress dtoAddress=new DtoAddress();
        
        address.setCreateTime(new Date());
        
        //BeanUtils.copyProperties(request.getCustomer().getAddress(), address);
        address.setStreet(request.getCustomer().getAddress().getStreet());
        address.setCity(request.getCustomer().getAddress().getCity());
        address.setDistrict(request.getCustomer().getAddress().getDistrict());
       
        //address.setCustomer(customer);
        customer.setAddress(address);
        

        return address;
    }
    
    public DtoAddress saveAddress(AuthRequest request,Customer customer) {
    	DtoAddress dtoAddress=new DtoAddress();
    	Address savedAddress=addressRepository.save(createAddress(request,customer));
    	
    	BeanUtils.copyProperties(savedAddress, dtoAddress);
    	
    	return dtoAddress;
    }
    
}
