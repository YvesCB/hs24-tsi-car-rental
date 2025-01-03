package ch.juventus.car_rental.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.repository.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final TypeService typeService;

    public CarService(CarRepository carRepository, TypeService typeService) {
        this.carRepository = carRepository;
        this.typeService = typeService;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void existsById(Long id) {
        boolean exists = carRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException();
        }
    }

    public Car create(Car car) {
        if (car.getType() != null && car.getType().getId() != null) {
            CarType type = typeService.findById(car.getType().getId());
            car.setType(type);
        }

        List<Car> cars = carRepository.findAll();
        for (Car existingCar : cars) {
            if (car.compare(existingCar)) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                        "Name, Type and Brand cannot all overlap with existing car.");
            }
        }
        return carRepository.save(car);
    }

    public Car update(Long id, Car carDetails) {
        Car car = findById(id);

        car.setName(carDetails.getName());
        car.setBrand(carDetails.getBrand());
        car.setYearOfConstruction(carDetails.getYearOfConstruction());
        car.setAutomatic(carDetails.isAutomatic());
        car.setType(carDetails.getType());

        if (carDetails.getType() != null && carDetails.getType().getId() != null) {
            CarType type = typeService.findById(carDetails.getType().getId());
            car.setType(type);
        }

        return carRepository.save(car);
    }

    public void delete(Long id) {
        Car car = findById(id);
        carRepository.delete(car);
    }

}
