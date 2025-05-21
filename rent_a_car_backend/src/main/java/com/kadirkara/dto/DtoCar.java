package com.kadirkara.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.kadirkara.enums.CarStatusType;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCar extends DtoBase {

    private String brand;

    private String bagajHacmi;

    private Integer modelYear;

    private String vites;

    private String yakıt;

    private String image;

    private boolean camTavan;

    private boolean parkSensörü;

    private String kasaTipi;

    private String motorHacmi;

    private String beygirGücü;

    private boolean klima;

    private String model;

    private CarStatusType carStatusType; 

    private boolean açılırTavan;

    private boolean abs;

    private BigDecimal officePrice;

    private BigDecimal onlinePrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
