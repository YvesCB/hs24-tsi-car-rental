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
import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {
    Logger logger = LoggerFactory.getLogger(CarService.class);

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

        if (car.getName() == "" || car.getBrand() == "") {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Name and Brand cannot be empty");
        }

        List<Car> cars = carRepository.findAll();
        for (Car existingCar : cars) {
            if (car.compare(existingCar)) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                        "Name, Type and Brand cannot all overlap with existing car.");
            }
        }

        Car newCar = carRepository.save(car);
        logger.info("Created new car. ID: " + newCar.getId());
        return newCar;
    }

    public Car update(Long id, Car carDetails) {
        Car car = findById(id);

        if (car.getBookings().size() > 0) { // we do not change price for booked cars
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Price of cars with prior or future bookings cannot be changed.");
        }

        car.setName(carDetails.getName());
        car.setBrand(carDetails.getBrand());
        car.setYearOfConstruction(carDetails.getYearOfConstruction());
        car.setAutomatic(carDetails.isAutomatic());
        car.setType(carDetails.getType());
        car.setActive(carDetails.isActive());

        if (carDetails.getType() != null && carDetails.getType().getId() != null) {
            CarType type = typeService.findById(carDetails.getType().getId());
            car.setType(type);
        }

        Car updatedCar = carRepository.save(car);
        logger.info("Updated car. ID: " + updatedCar.getId());
        return updatedCar;
    }

    public Car active(Long id) {
        Car car = findById(id);
        car.setActive(true);

        Car updatedCar = carRepository.save(car);
        logger.info("Activated car. ID: " + updatedCar.getId());
        return updatedCar;
    }

    public Car inActive(Long id) {
        Car car = findById(id);
        if (car.hasCurrentOrFutureBooking()) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Car with current or future booking cannot be made inactive.");
        }
        car.setActive(false);

        Car updatedCar = carRepository.save(car);
        logger.info("Activated car. ID: " + updatedCar.getId());
        return updatedCar;
    }

    public void delete(Long id) {
        Car car = findById(id);
        if (car != null && car.getBookings().size() != 0) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "A car with existing bookings cannot be deleted. Deactive it instead.");
        }

        logger.info("Deleted car. ID: " + id);
        carRepository.delete(car);
    }

}
