package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoPickupLocation;
import com.kadirkara.service.PickupLocationService;

@RestController
@RequestMapping("/api/customer/pickup-locations")
public class RestCustomerPickupLocationController {

	  private final PickupLocationService pickupLocationService;

	    public RestCustomerPickupLocationController(PickupLocationService pickupLocationService) {
	        this.pickupLocationService = pickupLocationService;
	    }
	
	  @GetMapping("/list")
	    public ResponseEntity<List<DtoPickupLocation>> getAllPickupLocationCars() {
	        return ResponseEntity.ok(pickupLocationService.getAllPickupLocations());
	    }
}
