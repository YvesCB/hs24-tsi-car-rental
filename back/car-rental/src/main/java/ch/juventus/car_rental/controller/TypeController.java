package ch.juventus.car_rental.controller;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.TypeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<CarType> getAllTypes() {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarType> getTypeById(@PathVariable Long id) {
        CarType type = typeService.findById(id);
        return ResponseEntity.ok(type);
    }

    @PostMapping
    public ResponseEntity<CarType> createType(@RequestBody CarType request) {
        CarType type = typeService.create(request);
        return ResponseEntity.ok(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarType> updateType(@PathVariable Long id, @RequestBody CarType request) {
        CarType type = typeService.update(id, request);
        return ResponseEntity.ok(type);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
