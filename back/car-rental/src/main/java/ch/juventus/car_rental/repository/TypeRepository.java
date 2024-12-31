package ch.juventus.car_rental.repository;

import ch.juventus.car_rental.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
}
