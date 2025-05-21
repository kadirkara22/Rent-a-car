package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.service.PickupLocationCarService;
import com.kadirkara.service.PickupLocationService;

@RestController
@RequestMapping("/api/customer/pickup-locations")
public class RestCustomerPickupLocationCarController {

	 private final PickupLocationCarService pickupLocationCarService;

	    public RestCustomerPickupLocationCarController(PickupLocationCarService pickupLocationCarService) {
	        this.pickupLocationCarService = pickupLocationCarService;
	    }
	    
	    @GetMapping("/{locationId}/cars")
        public ResponseEntity<List<DtoCar>> getCarsByLocation(@PathVariable Long locationId) {
            List<DtoCar> cars = pickupLocationCarService.getCarsByLocation(locationId);
            return ResponseEntity.ok(cars);
        }
}
