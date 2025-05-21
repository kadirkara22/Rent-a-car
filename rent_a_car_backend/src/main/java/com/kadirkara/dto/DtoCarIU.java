package com.kadirkara.dto;

import java.math.BigDecimal;

import com.kadirkara.enums.CarStatusType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DtoCarIU {

	@NotNull
    private String brand;
	@NotNull
    private String bagajHacmi;
	@NotNull
    private Integer modelYear;
	@NotNull
    private String vites;
	@NotNull
    private String yakıt;
	@NotNull
    private String image;
	@NotNull
    private boolean camTavan;
	@NotNull
    private boolean parkSensörü;
	@NotNull
    private String kasaTipi;
	@NotNull
    private String motorHacmi;
	@NotNull
    private String beygirGücü;
	@NotNull
    private boolean klima;
	@NotNull
    private String model;
	@NotNull
    private CarStatusType carStatusType; 
	@NotNull
    private boolean açılırTavan;
	@NotNull
    private boolean abs;
	@NotNull
    private BigDecimal officePrice;
	@NotNull
    private BigDecimal onlinePrice;
}
