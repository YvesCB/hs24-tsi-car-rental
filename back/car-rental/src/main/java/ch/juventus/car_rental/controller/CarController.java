package ch.juventus.car_rental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.CarService;

/**
 * CarController
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        // Resolve type id

        Car savedCar = carService.create(car);
        return ResponseEntity.ok(savedCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {

        Car updatedCar = carService.update(id, carDetails);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
