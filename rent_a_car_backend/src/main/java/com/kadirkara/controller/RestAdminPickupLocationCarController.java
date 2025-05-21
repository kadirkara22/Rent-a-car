package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.service.PickupLocationCarService;

@RestController
@RequestMapping("/api/admin/pickup-locations")
public class RestAdminPickupLocationCarController {

    private final PickupLocationCarService pickupLocationCarService;

    public RestAdminPickupLocationCarController(PickupLocationCarService pickupLocationCarService) {
        this.pickupLocationCarService = pickupLocationCarService;
    }

    @PostMapping("/{locationId}/assign-car/{carId}")
    public ResponseEntity<String> assignCarToLocation(
            @PathVariable Long locationId,
            @PathVariable Long carId,
            @RequestParam(required = false, defaultValue = "AVAILABLE") String status) {
        pickupLocationCarService.assignCarToLocation(locationId, carId, status);
        return ResponseEntity.ok("Car successfully assigned to the location.");
    }
     

      
        @GetMapping("/{locationId}/cars")
        public ResponseEntity<List<DtoCar>> getCarsByLocation(@PathVariable Long locationId) {
            List<DtoCar> cars = pickupLocationCarService.getCarsByLocation(locationId);
            return ResponseEntity.ok(cars);
        }

      
        @GetMapping("/{locationId}/cars/{carId}")
        public ResponseEntity<DtoCar> getCarByLocationAndCarId(@PathVariable Long locationId, @PathVariable Long carId) {
            DtoCar dtoCar = pickupLocationCarService.getCarByLocationAndCarId(locationId, carId);
            return ResponseEntity.ok(dtoCar);
        }

        
        @PutMapping("/{locationId}/update/{carId}")
        public ResponseEntity<Void> updateCarStatus(@PathVariable Long locationId, @PathVariable Long carId, @RequestParam String status) {
            pickupLocationCarService.updateCarStatus(locationId, carId, status);
            return ResponseEntity.ok().build();
        }

        
        @DeleteMapping("/{locationId}/delete/{carId}")
        public ResponseEntity<Void> removeCarFromLocation(@PathVariable Long locationId, @PathVariable Long carId) {
            pickupLocationCarService.removeCarFromLocation(locationId, carId);
            return ResponseEntity.noContent().build();
        } 
    
 
}
