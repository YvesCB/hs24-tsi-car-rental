package ch.juventus.car_rental.controller;

import ch.juventus.car_rental.model.*;
import ch.juventus.car_rental.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking Controller", description = "Provides the CRUD endpoints for Bookings.")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all Bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bookings")
    })
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @Operation(summary = "Get a booking by car id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings for given car id."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "404", description = "Car with id not found.")
    })
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCarId(@PathVariable Long carId) {
        List<Booking> bookings = bookingService.getAllByCarId(carId);
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @Operation(summary = "Create a new booking", description = "Booking dates cannot overlap with existing bookings of that car. If they do a 400 will be returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created.", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car associated with booking not found.")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingCreateDTO request) {
        Booking incoming = Mapper.toEntity(request);
        Booking booking = bookingService.create(incoming, request.getCarId());
        return ResponseEntity.ok(Mapper.toDto(booking));
    }

    @Operation(summary = "Delete a booking", description = "Only bookings in the future can be deleted with this endpoint. This is to guarantee the integrety of the bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Booking deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car associated with booking not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(summary = "Force delete a booking", description = "For testing and debugging purposes. Might be disabled later.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Booking deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid json provided."),
            @ApiResponse(responseCode = "404", description = "Car associated with booking not found.")
    })
    @DeleteMapping("/force/{id}")
    public ResponseEntity<Void> forceDeleteBooking(@PathVariable Long id) {
        bookingService.forceDelete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
