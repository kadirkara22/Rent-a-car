package com.kadirkara.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoCarIU;
import com.kadirkara.enums.CarStatusType;
import com.kadirkara.model.Car;
import com.kadirkara.service.CarService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin/cars")
public class RestCarController {

	private final CarService carService;

    public RestCarController(CarService carService) {
        this.carService = carService;
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public ResponseEntity<DtoCar> addCar(@Valid @RequestBody DtoCarIU dtoCarIU) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.addCar(dtoCarIU));
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/list")
    public ResponseEntity<List<DtoCar>> getAllCars() {
        List<DtoCar> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }
    
    
  
    @GetMapping("/list/{id}")
    public ResponseEntity<DtoCar> getCarById(@PathVariable(name="id") Long id) {
        DtoCar dtoCar = carService.getCarById(id);
        return ResponseEntity.ok(dtoCar);
    }

   
    @PutMapping("/update/{id}")
    public ResponseEntity<DtoCar> updateCar(@PathVariable(name="id") Long id, @RequestBody DtoCarIU dtoCarIU) {
        DtoCar dtoCar = carService.updateCar(id, dtoCarIU);
        return ResponseEntity.ok(dtoCar);
    }

   
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable(name = "id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    
    @PatchMapping("/{id}/status")
    public ResponseEntity<DtoCar> updateCarStatus(@PathVariable Long id, @RequestParam CarStatusType status) {
        DtoCar updatedCar = carService.updateCarStatus(id, status);
        return ResponseEntity.ok(updatedCar);
    }
/*
    // Fiyat aralığına göre araçları filtrele
    @GetMapping("/filter-by-price")
    public ResponseEntity<List<Car>> getCarsByPriceRange(
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice) {
        List<Car> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(cars);
    }
    */
  
}
