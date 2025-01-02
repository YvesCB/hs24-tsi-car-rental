package ch.juventus.car_rental.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Booking;
import ch.juventus.car_rental.repository.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {
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

    // private List<Booking> findAllByCarId(Long carId) {
    // List<Booking> bookings = bookingRepository.findAll();
    // List<Booking> bookingsByCarId = bookings.stream().filter((booking) ->
    // booking.getCar().getId() == carId)
    // .collect(Collectors.toList());
    //
    // return bookingsByCarId;
    // }

    public List<Booking> getAllByCarId(Long carId) {
        carService.existsById(carId);
        // List<Booking> bookings = findAllByCarId(carId);
        List<Booking> bookings = bookingRepository.findAllByCarId(carId);
        return bookings;
    }

    public Booking create(Booking booking) {
        // Validate: fromDate should not be after toDate
        if (booking.getFromDate().isAfter(booking.getToDate())) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Start date needs to be before End date.");
        }

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

        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        Booking booking = findById(id);
        bookingRepository.delete(booking);
    }
}
