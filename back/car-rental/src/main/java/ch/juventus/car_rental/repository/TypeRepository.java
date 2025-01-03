package ch.juventus.car_rental.repository;

import ch.juventus.car_rental.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<CarType, Long> {
}
