package ch.juventus.car_rental.repository;

import ch.juventus.car_rental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
