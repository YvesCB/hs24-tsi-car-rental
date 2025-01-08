package ch.juventus.car_rental.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.CarService;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Controller", description = "Provides the CRUD endpoints for Cars.")
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Get all Cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bookings")
    })
    @GetMapping
    public List<Car> findAll() {
        return carService.findAll();
    }

    @Operation(summary = "Get cars with url param filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bookings"),
            @ApiResponse(responseCode = "400", description = "Badly formatted request. Some conditions apply for the parameters. MinPrice needs to be Lower than MaxPrice. Start needs to be before or equal to End."),
    })
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

    @Operation(summary = "Get car by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car with given id."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "404", description = "Car with given id not found."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.findById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Create a new car.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created car."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
    })
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.create(car);
        return ResponseEntity.ok(savedCar);
    }

    @Operation(summary = "Update a given car by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated car."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car with given id not found."),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car updatedCar = carService.update(id, carDetails);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Change car with given id to active.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated car."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car with given id not found."),
    })
    @PatchMapping("/active/{id}")
    public ResponseEntity<Car> updateActiveCar(@PathVariable Long id) {
        Car updatedCar = carService.active(id);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Change car with given id to inactive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated car."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car with given id not found."),
    })
    @PatchMapping("/inactive/{id}")
    public ResponseEntity<Car> updateInActiveCar(@PathVariable Long id) {
        Car updatedCar = carService.inActive(id);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Delete a given car.", description = "Can only delete cars without bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Car deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car with given id not found."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
