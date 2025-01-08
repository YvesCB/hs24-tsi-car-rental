package ch.juventus.car_rental.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.juventus.car_rental.model.Booking;
import ch.juventus.car_rental.model.BookingCreateDTO;
import ch.juventus.car_rental.model.BookingDTO;
import ch.juventus.car_rental.model.Mapper;
import ch.juventus.car_rental.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
            @ApiResponse(responseCode = "200", description = "All bookings", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookingDTO.class)))
            })
    })
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @Operation(summary = "Get bookings by car id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bookings for given car id.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookingDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid id provided.", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Car with id not found.", content = { @Content() })
    })
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCarId(
            @Parameter(description = "Id of the car") @PathVariable Long carId) {
        List<Booking> bookings = bookingService.getAllByCarId(carId);
        List<BookingDTO> bookingDTOs = bookings.stream().map((booking) -> Mapper.toDto(booking))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }

    @Operation(summary = "Create a new booking", description = "Booking dates cannot overlap with existing bookings of that car. If they do a 400 will be returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid json provided.", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Car associated with booking not found.", content = {
                    @Content() })
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details for the new booking", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCreateDTO.class), examples = @ExampleObject(value = """
                    {
                      "fromDate": "2025-06-01",
                      "toDate": "2025-06-02",
                      "customerName": "Bob Man",
                      "carId": 2
                    }
                    """))) BookingCreateDTO request) {
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
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "Id of booking to delete") @PathVariable Long id) {
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
    public ResponseEntity<Void> forceDeleteBooking(
            @Parameter(description = "Id of booking to delete.") @PathVariable Long id) {
        bookingService.forceDelete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
