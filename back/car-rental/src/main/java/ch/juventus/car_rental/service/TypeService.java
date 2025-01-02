package ch.juventus.car_rental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.juventus.car_rental.model.Type;
import ch.juventus.car_rental.repository.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public Type findById(Long id) {
        return typeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Type create(Type type) {
        return typeRepository.save(type);
    }

    public Type update(Long id, Type typeDetails) {
        Type type = findById(id);

        type.setName(typeDetails.getName());
        type.setDescription(typeDetails.getDescription());

        return typeRepository.save(type);
    }

    public void delete(Long id) {
        Type type = findById(id);
        typeRepository.delete(type);
    }
}
