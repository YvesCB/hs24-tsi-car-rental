package ch.juventus.car_rental.controller;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    @Autowired
    private TypeRepository typeRepository;

    @GetMapping
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Type getTypeById(@PathVariable Long id) {
        return typeRepository.findById(id).orElseThrow(() -> new RuntimeException("Type not found"));
    }

    @PostMapping
    public Type createType(@RequestBody Type type) {
        return typeRepository.save(type);
    }

    @PutMapping("/{id}")
    public Type updateType(@PathVariable Long id, @RequestBody Type typeDetails) {
        Type type = typeRepository.findById(id).orElseThrow(() -> new RuntimeException("Type not found"));

        type.setName(typeDetails.getName());
        type.setDescription(typeDetails.getDescription());

        return typeRepository.save(type);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        Type type = typeRepository.findById(id).orElseThrow(() -> new RuntimeException("Type not found"));
        typeRepository.delete(type);
    }
}
