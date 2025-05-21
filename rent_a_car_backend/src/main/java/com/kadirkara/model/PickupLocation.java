package com.kadirkara.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pickup_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PickupLocation extends BaseEntity{

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "street")
    private String street;

    
    @JoinColumn(name = "city_id")
    private String city;

    @Column(name = "district")
    private String district;
    
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
}
