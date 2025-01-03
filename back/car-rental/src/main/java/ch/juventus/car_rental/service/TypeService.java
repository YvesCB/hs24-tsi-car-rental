package ch.juventus.car_rental.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.repository.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<CarType> findAll() {
        return typeRepository.findAll();
    }

    public CarType findById(Long id) {
        return typeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public CarType create(CarType type) {
        List<CarType> types = typeRepository.findAll();
        for (CarType existingType : types) {
            if (type.compare(existingType)) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                        "Name and Description cannot all overlap with existing type.");
            }
        }
        return typeRepository.save(type);
    }

    public CarType update(Long id, CarType typeDetails) {
        CarType type = findById(id);

        type.setName(typeDetails.getName());
        type.setDescription(typeDetails.getDescription());

        return typeRepository.save(type);
    }

    public void delete(Long id) {
        CarType type = findById(id);
        typeRepository.delete(type);
    }
}
