package ch.juventus.car_rental.controller;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
@Tag(name = "CarType Controller", description = "Provides the CRUD endpoints for CarType.")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @Operation(summary = "Get all CarType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All CarType."),
    })
    @GetMapping
    public List<CarType> getAllTypes() {
        return typeService.findAll();
    }

    @Operation(summary = "Get CarType by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType for given car id."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarType> getTypeById(@PathVariable Long id) {
        CarType type = typeService.findById(id);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Create a new CarType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType created."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
    })
    @PostMapping
    public ResponseEntity<CarType> createType(@RequestBody CarType request) {
        CarType type = typeService.create(request);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Update the CarType with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType updated."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarType> updateType(@PathVariable Long id, @RequestBody CarType request) {
        CarType type = typeService.update(id, request);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Delete the CarType with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "CarType deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
