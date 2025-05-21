package com.kadirkara.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoRentedCar;
import com.kadirkara.dto.DtoRentedCarIU;
import com.kadirkara.service.CarService;
import com.kadirkara.service.RentedCarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer/rented-cars")
public class RestRentedCarController {

    private final RentedCarService rentedCarService;

    public RestRentedCarController(RentedCarService rentedCarService) {
        this.rentedCarService = rentedCarService;
    }
    
    @PostMapping("/rent")
    public ResponseEntity<DtoRentedCar> rentCar(@Valid @RequestBody DtoRentedCarIU dtoRentedCarIU) {

        DtoRentedCar dtoRentedCar = rentedCarService.rentCar(dtoRentedCarIU);
        return ResponseEntity.ok(dtoRentedCar);
    }
    
   

    
    @GetMapping("/list/{customer_id}")
    public ResponseEntity<List<DtoRentedCar>> getRentedCarsByCustomer(@PathVariable(name="customer_id") Long customer_id) {
        List<DtoRentedCar> rentedCars = rentedCarService.getRentedCarsByCustomer(customer_id);
        return ResponseEntity.ok(rentedCars);
    }
    @GetMapping("/list")
    public ResponseEntity<List<DtoRentedCar>> getAllRents() {
        List<DtoRentedCar> rentedCars = rentedCarService.getAllRents();
        return ResponseEntity.ok(rentedCars);
    }
}


