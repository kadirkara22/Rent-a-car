package com.kadirkara.dto;

import org.springframework.beans.BeanUtils;

import com.kadirkara.model.Address;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAddress extends DtoBase{

	    private String street;

	    private String city;

	    private String district;
	    
}
