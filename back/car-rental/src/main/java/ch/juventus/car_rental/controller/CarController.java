package ch.juventus.car_rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.repository.*;

/**
 * CarController
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TypeRepository typeRepository;

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        // Resolve type id
        if (car.getType() != null && car.getType().getId() != null) {
            Type type = typeRepository.findById(car.getType().getId())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            car.setType(type);
        }
        return carRepository.save(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));

        car.setName(carDetails.getName());
        car.setBrand(carDetails.getBrand());
        car.setYearOfConstruction(carDetails.getYearOfConstruction());
        car.setAutomatic(carDetails.isAutomatic());
        car.setType(carDetails.getType());

        if (carDetails.getType() != null && carDetails.getType().getId() != null) {
            Type type = typeRepository.findById(carDetails.getType().getId())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            car.setType(type);
        }

        return carRepository.save(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        carRepository.delete(car);
    }
}
