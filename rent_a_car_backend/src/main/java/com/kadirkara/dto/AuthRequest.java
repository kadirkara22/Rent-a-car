package com.kadirkara.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	@NotEmpty
    private String email;
	@NotEmpty
	private String password;
	
	private DtoCustomerIU customer; 
}
