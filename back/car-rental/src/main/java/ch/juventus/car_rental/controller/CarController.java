package ch.juventus.car_rental.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
            @ApiResponse(responseCode = "200", description = "All bookings", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class))))
    })
    @GetMapping
    public List<Car> findAll() {
        return carService.findAll();
    }

    @Operation(summary = "Get cars with url param filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bookings", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
            @ApiResponse(responseCode = "400", description = "Badly formatted request. Some conditions apply for the parameters. MinPrice needs to be Lower than MaxPrice. Start needs to be before or equal to End.", content = @Content()),
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Car>> filterCars(
            @Parameter(description = "Minimum price per day of car") @RequestParam(required = false) Integer minPrice,
            @Parameter(description = "Maximum price per day of car") @RequestParam(required = false) Integer maxPrice,
            @Parameter(description = "Brand of car") @RequestParam(required = false) String brand,
            @Parameter(description = "Type name of car") @RequestParam(required = false) String typeName,
            @Parameter(description = "Available from date") @RequestParam(required = false) LocalDate start,
            @Parameter(description = "Available to date") @RequestParam(required = false) LocalDate end) {

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
            @ApiResponse(responseCode = "200", description = "Car with given id.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Car with given id not found.", content = @Content()),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@Parameter(description = "Id of the car") @PathVariable Long id) {
        Car car = carService.findById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Create a new car.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Type of car not found", content = @Content()),
    })
    @PostMapping
    public ResponseEntity<Car> createCar(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The car to create.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class), examples = @ExampleObject(value = """
                    {
                      "name": "Model 3",
                      "brand": "Tesla",
                      "yearOfConstruction": 2022,
                      "automatic": true,
                      "pricePerDay": 500,
                      "active": true,
                      "type": {
                        "id": 100
                      }
                    }
                            """))) Car car) {
        Car savedCar = carService.create(car);
        return ResponseEntity.ok(savedCar);
    }

    @Operation(summary = "Update a given car by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Car with given id not found.", content = @Content()),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated car data.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class), examples = @ExampleObject(value = """
                    {
                      "name": "Model 3",
                      "brand": "Tesla",
                      "yearOfConstruction": 2022,
                      "automatic": true,
                      "pricePerDay": 500,
                      "active": true,
                      "type": {
                        "id": 100
                      }
                    }
                            """))) Car carDetails) {
        Car updatedCar = carService.update(id, carDetails);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Change car with given id to active.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated car.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Car with given id not found.", content = @Content()),
    })
    @PatchMapping("/active/{id}")
    public ResponseEntity<Car> updateActiveCar(
            @Parameter(description = "Id of car to set to active") @PathVariable Long id) {
        Car updatedCar = carService.active(id);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Change car with given id to inactive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated car.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Car with given id not found.", content = @Content()),
    })
    @PatchMapping("/inactive/{id}")
    public ResponseEntity<Car> updateInActiveCar(
            @Parameter(description = "Id of car to set to inactive") @PathVariable Long id) {
        Car updatedCar = carService.inActive(id);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Delete a given car.", description = "Can only delete cars without bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Car deleted.", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Car with given id not found.", content = @Content()),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@Parameter(description = "Id of car to delete") @PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
