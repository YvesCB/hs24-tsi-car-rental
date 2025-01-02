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
    public List<Type> getAllTypes() {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Long id) {
        Type type = typeService.findById(id);
        return ResponseEntity.ok(type);
    }

    @PostMapping
    public ResponseEntity<Type> createType(@RequestBody Type request) {
        Type type = typeService.create(request);
        return ResponseEntity.ok(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Long id, @RequestBody Type request) {
        Type type = typeService.update(id, request);
        return ResponseEntity.ok(type);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
