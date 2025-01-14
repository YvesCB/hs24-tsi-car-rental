package ch.juventus.car_rental.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Booking;
import ch.juventus.car_rental.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {
    Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final CarService carService;

    public BookingService(BookingRepository bookingRepository, CarService carService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Booking> findOverlapTimeRange(LocalDate start, LocalDate end) {
        List<Booking> bookings = findAll();
        List<Booking> filteredBookings = bookings.stream().filter((booking) -> {
            return booking.getFromDate().isBefore(end) && booking.getToDate().isAfter(start);
        }).collect(Collectors.toList());

        return filteredBookings;
    }

    public List<Booking> getAllByCarId(Long carId) {
        carService.existsById(carId);
        List<Booking> bookings = bookingRepository.findAllByCarId(carId);
        return bookings;
    }

    public Booking create(Booking booking, Long carId) {
        // Validate: fromDate should not be after toDate
        if (booking.getFromDate().isAfter(booking.getToDate())) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Start date needs to be before End date.");
        }

        booking.setCar(carService.findById(carId));

        // Validate: Check for overlapping bookings for the same car
        List<Booking> existingBookings = bookingRepository.findAllByCarId(booking.getCar().getId());
        for (Booking existingBooking : existingBookings) {
            boolean overlaps = booking.getFromDate().isBefore(existingBooking.getToDate()) &&
                    booking.getToDate().isAfter(existingBooking.getFromDate());
            if (overlaps) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                        "Booking dates overlap with an existing booking.");
            }
        }

        // Save the booking if validations pass
        carService.existsById(booking.getCar().getId());

        Booking newbooking = bookingRepository.save(booking);
        logger.info("Created new Booking. ID: " + newbooking.getId() + " for CarID: " + newbooking.getCar().getId());
        return newbooking;
    }

    public void delete(Long id) {
        Booking booking = findById(id);
        LocalDate now = LocalDate.now();
        if (booking.getFromDate().isAfter(now)) {
            bookingRepository.delete(booking);
        } else {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "Only future bookings can be deleted for consistancy.");
        }
        logger.info("Deleted Booking. ID: " + id);
    }

    public void forceDelete(Long id) {
        Booking booking = findById(id);
        bookingRepository.delete(booking);
        logger.info("Deleted Booking. ID: " + id);
    }
}
