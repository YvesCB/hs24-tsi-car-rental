package ch.juventus.car_rental.controller;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.BookingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCarId(@PathVariable Long carId) {
        List<Booking> bookings = bookingService.getAllByCarId(carId);
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingCreateDTO request) {
        Booking incoming = Mapper.toEntity(request);
        Booking booking = bookingService.create(incoming, request.getCarId());
        return ResponseEntity.ok(Mapper.toDto(booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/force/{id}")
    public ResponseEntity<Void> forceDeleteBooking(@PathVariable Long id) {
        bookingService.forceDelete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
