package com.kadirkara.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoPickupLocation extends DtoBase{

	    private String locationName;

	    private String street;

	    
	    
	    private String city;


	    private String district;
}
