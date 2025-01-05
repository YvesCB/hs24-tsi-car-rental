package ch.juventus.car_rental.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/filter")
    public ResponseEntity<List<Car>> filterCars(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end) {

        if (minPrice != null && maxPrice != null && minPrice > maxPrice
                || start != null && end != null && start.isAfter(end)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Max price cannot be lower than min price. Start date cannot be after end date.");
        }
        if ((start == null) != (end == null)) { // one is null and the other isn't
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Provide start and end date or no dates at all.");
        }

        List<Car> cars = carService.findAll();

        List<Car> filteredCars = cars.stream()
                .filter(car -> (minPrice == null || car.getPricePerDay() >= minPrice)) // Filter by minimum price
                .filter(car -> (maxPrice == null || car.getPricePerDay() <= maxPrice)) // Filter by maximum price
                .filter(car -> (brand == null || car.getBrand().equalsIgnoreCase(brand))) // Filter by brand
                .filter(car -> (typeName == null
                        || (car.getType() != null && car.getType().getName().equalsIgnoreCase(typeName))))
                .filter(car -> (start == null || end == null || car.availableDuring(start, end)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredCars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        try {
            Car car = carService.findById(id);
            return ResponseEntity.ok(car);
        } catch (EntityNotFoundException e) {
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
