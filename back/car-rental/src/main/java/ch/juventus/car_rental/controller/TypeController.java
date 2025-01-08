package ch.juventus.car_rental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
            @ApiResponse(responseCode = "200", description = "All CarTypes", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarType.class)))
            })
    })
    @GetMapping
    public List<CarType> getAllTypes() {
        return typeService.findAll();
    }

    @Operation(summary = "Get CarType by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType for given car id.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarType.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarType> getTypeById(@Parameter(description = "Id of CarType") @PathVariable Long id) {
        CarType type = typeService.findById(id);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Create a new CarType", description = "If name and description match existing entry, the creation will be rejected with a 400.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarType.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
    })
    @PostMapping
    public ResponseEntity<CarType> createType(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Body for creating new CarType", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarType.class), examples = @ExampleObject(value = """
                    {
                      "name": "Hatch back",
                      "descript": "Small cars with plenty of storage space."
                    }
                            """))) CarType request) {
        CarType type = typeService.create(request);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Update the CarType with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CarType updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarType.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarType> updateType(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Body for creating new CarType", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarType.class), examples = @ExampleObject(value = """
                    {
                      "name": "Hatch back",
                      "descript": "Small cars with plenty of storage space."
                    }
                            """))) CarType request,
            @Parameter(description = "Id of CarType to update") @PathVariable Long id) {
        CarType type = typeService.update(id, request);
        return ResponseEntity.ok(type);
    }

    @Operation(summary = "Delete the CarType with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "CarType deleted.", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Invalid id provided.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "CarType with id not found.", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@Parameter(description = "Id of car to delete.") @PathVariable Long id) {
        typeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
