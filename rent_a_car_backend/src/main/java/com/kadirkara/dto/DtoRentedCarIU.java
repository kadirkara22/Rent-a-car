package com.kadirkara.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoRentedCarIU {
	@NotNull
	private DtoPickupLocation pickupLocation; 
	@NotNull
	private Long customerId;
	@NotNull
	private Long carId;
	@NotNull
	private LocalDateTime startDate; 
	@NotNull
	private LocalDateTime endDate; 
}


