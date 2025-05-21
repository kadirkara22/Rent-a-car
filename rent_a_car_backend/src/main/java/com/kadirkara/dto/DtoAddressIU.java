package com.kadirkara.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoAddressIU {
	@NotEmpty
	 private String street;
	@NotEmpty
    private String city;
	@NotEmpty
	private String district;
}
