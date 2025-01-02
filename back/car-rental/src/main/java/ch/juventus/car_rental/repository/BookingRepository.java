package ch.juventus.car_rental.repository;

import ch.juventus.car_rental.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByCarId(Long carId);
}
