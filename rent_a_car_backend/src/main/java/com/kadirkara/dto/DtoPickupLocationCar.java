package com.kadirkara.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPickupLocationCar extends DtoBase{

	    @NotNull(message = "Pickup Location is required")
	    private Long pickupLocationId; 

	    @NotNull(message = "Car is required")
	    private Long carId;  

	    @NotNull(message = "Status is required")
	    private String status; 
}
