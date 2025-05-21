package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoPickupLocation;
import com.kadirkara.dto.DtoPickupLocationIU;
import com.kadirkara.service.PickupLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/pickup-locations")
public class RestAdminPickupLocationController {

    private final PickupLocationService pickupLocationService;

    public RestAdminPickupLocationController(PickupLocationService pickupLocationService) {
        this.pickupLocationService = pickupLocationService;
    }

   
    @PostMapping("/add")
    public ResponseEntity<DtoPickupLocation> createPickupLocation(@Valid @RequestBody DtoPickupLocationIU dtoPickupLocationIU) {
        return ResponseEntity.ok(pickupLocationService.createPickupLocation(dtoPickupLocationIU));
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<DtoPickupLocation> updatePickupLocation(@PathVariable(name="id") Long id, @RequestBody DtoPickupLocationIU dtoPickupLocationIU) {
        return ResponseEntity.ok(pickupLocationService.updatePickupLocation(id, dtoPickupLocationIU));
    }
    
    
    @GetMapping("/list/{id}")
    public ResponseEntity<DtoPickupLocation> getPickupLocationCarById(@PathVariable(name="id") Long id) {
        return ResponseEntity.ok(pickupLocationService.getPickupLocationById(id));
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<List<DtoPickupLocation>> getAllPickupLocationCars() {
        return ResponseEntity.ok(pickupLocationService.getAllPickupLocations());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePickupLocation(@PathVariable(name = "id") Long id) {
        pickupLocationService.deletePickupLocation(id);
        return ResponseEntity.noContent().build();
    }
    
}