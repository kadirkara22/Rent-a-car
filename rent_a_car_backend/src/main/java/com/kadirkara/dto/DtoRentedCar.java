package com.kadirkara.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoRentedCar extends DtoBase{

    private DtoPickupLocation pickupLocation; 

    private DtoCar car;  

    private DtoCustomer customer;

    private LocalDateTime startDate; 

    private LocalDateTime endDate; 
}
