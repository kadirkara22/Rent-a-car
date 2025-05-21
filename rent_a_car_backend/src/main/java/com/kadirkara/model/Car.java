package com.kadirkara.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.kadirkara.enums.CarStatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntity{


    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "bagaj_hacmi")
    private String bagajHacmi;

    @Column(name = "model_year")
    private Integer modelYear;

    @Column(name = "vites")
    private String vites;

    @Column(name = "yakıt")
    private String yakıt;

    @Column(name = "image")
    private String image;

    @Column(name = "cam_tavan")
    private boolean camTavan;

    @Column(name = "park_sesörü")
    private boolean parkSensörü;

    @Column(name = "kasa_tipi")
    private String kasaTipi;

    @Column(name = "motor_hacmi")
    private String motorHacmi;

    @Column(name = "beygir_gücü")
    private String beygirGücü;

    @Column(name = "klima")
    private boolean klima;

    @Column(name = "model")
    private String model;

    @Column(name = "car_status_type")
    @Enumerated(EnumType.STRING)
    private CarStatusType carStatusType;

    @Column(name = "acilir_tavan")
    private boolean açılırTavan;

    @Column(name = "abs")
    private boolean abs;

    @Column(name = "office_price", precision = 10, scale = 2)
    private BigDecimal officePrice;

    @Column(name = "online_price", precision = 10, scale = 2)
    private BigDecimal onlinePrice;
    
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
