package ch.juventus.car_rental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.CarService;
import jakarta.persistence.EntityNotFoundException;

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
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        try {
            Car car = carService.findById(id);
            return ResponseEntity.ok(car);
        } catch (EntityNotFoundException e) {
            // TODO: handle exception
            throw new HttpStatusException(HttpStatus.NOT_FOUND,
                    "The provided id does no correspond to a car in the database.");
        }
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        // Resolve type id

        try {
            Car savedCar = carService.create(car);
            return ResponseEntity.ok(savedCar);
        } catch (EntityNotFoundException e) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND,
                    "The provided id does no correspond to a type in the database.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {

        try {
            Car updatedCar = carService.update(id, carDetails);
            return ResponseEntity.ok(updatedCar);
        } catch (EntityNotFoundException e) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND,
                    "The provided id does no correspond to a car in the database.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
