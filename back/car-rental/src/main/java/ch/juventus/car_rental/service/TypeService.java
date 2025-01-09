package ch.juventus.car_rental.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.repository.CarRepository;
import ch.juventus.car_rental.repository.TypeRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TypeService {
    Logger logger = LoggerFactory.getLogger(TypeService.class);

    private final TypeRepository typeRepository;
    private final CarRepository carRepository;

    public TypeService(TypeRepository typeRepository, CarRepository carRepository) {
        this.typeRepository = typeRepository;
        this.carRepository = carRepository;
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
        CarType newCarType = typeRepository.save(type);
        logger.info("New CarType created. ID: " + newCarType.getId());
        return newCarType;
    }

    public CarType update(Long id, CarType typeDetails) {
        CarType type = findById(id);

        type.setName(typeDetails.getName());
        type.setDescription(typeDetails.getDescription());

        CarType updatedCarType = typeRepository.save(type);
        logger.info("CarType updated. ID: " + updatedCarType.getId());
        return updatedCarType;
    }

    public void delete(Long id) {
        CarType type = findById(id);
        List<Car> cars = carRepository.findAll();
        for (Car car : cars) {
            if (car.getType().getId() == id) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                        "Only types that are assigned to no cars can be deleted.");
            }
        }
        logger.info("CarType deleted. ID: " + id);
        typeRepository.delete(type);
    }
}
