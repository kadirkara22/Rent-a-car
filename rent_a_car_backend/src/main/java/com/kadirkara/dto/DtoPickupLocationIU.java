package com.kadirkara.dto;

import java.util.Date;

import com.kadirkara.model.Account;
import com.kadirkara.model.Address;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoPickupLocationIU {
	@NotNull
	private String locationName;
	@NotNull
    private String street;
	@NotNull
    private String city;

	@NotNull
    private String district;
}
