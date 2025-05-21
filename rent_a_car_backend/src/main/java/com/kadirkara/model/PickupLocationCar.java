package com.kadirkara.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pickup_location_car",
uniqueConstraints = {@UniqueConstraint(columnNames = {"pickup_location_id", "car_id"}, name = "uq_pickup_location_car")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PickupLocationCar extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "pickup_location_id", referencedColumnName = "id")
    private PickupLocation pickupLocation;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "status")
    private String status;
    
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

}
