package ch.juventus.car_rental.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Booking;
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CarService carService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Booking createBooking(Long id, LocalDate fromDate, LocalDate toDate, Car car) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setFromDate(fromDate);
        booking.setToDate(toDate);
        booking.setCar(car);
        return booking;
    }

    private Car createCar(Long id) {
        Car car = new Car();
        car.setId(id);
        return car;
    }

    @Test
    void testFindAll() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(createBooking(1L, LocalDate.now(), LocalDate.now().plusDays(1), createCar(1L)));
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.findAll();

        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Booking booking = createBooking(1L, LocalDate.now(), LocalDate.now().plusDays(1), createCar(1L));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.findById(1L));
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testFindOverlapTimeRange() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(createBooking(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5), createCar(1L)));
        bookings.add(createBooking(2L, LocalDate.of(2025, 1, 3), LocalDate.of(2025, 1, 7), createCar(1L)));
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.findOverlapTimeRange(LocalDate.of(2025, 1, 4), LocalDate.of(2025, 1, 6));

        assertEquals(2, result.size());
    }

    @Test
    void testCreate_Success() {
        Car car = createCar(1L);
        Booking booking = createBooking(null, LocalDate.now(), LocalDate.now().plusDays(1), null);
        when(carService.findById(1L)).thenReturn(car);
        when(bookingRepository.findAllByCarId(1L)).thenReturn(new ArrayList<>());
        when(bookingRepository.save(booking))
                .thenReturn(createBooking(1L, booking.getFromDate(), booking.getToDate(), car));

        Booking result = bookingService.create(booking, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getCar().getId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreate_InvalidDateRange() {
        Booking booking = createBooking(null, LocalDate.now().plusDays(1), LocalDate.now(), null);

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> bookingService.create(booking, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Start date needs to be before End date.", exception.getMessage());
    }

    @Test
    void testCreate_OverlappingBooking() {
        Car car = createCar(1L);
        Booking existingBooking = createBooking(1L, LocalDate.now(), LocalDate.now().plusDays(2), car);
        Booking newBooking = createBooking(null, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), car);
        when(carService.findById(1L)).thenReturn(car);
        when(bookingRepository.findAllByCarId(1L)).thenReturn(List.of(existingBooking));

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> bookingService.create(newBooking, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Booking dates overlap with an existing booking.", exception.getMessage());
    }

    @Test
    void testDelete_FutureBooking() {
        Booking booking = createBooking(1L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), createCar(1L));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        assertDoesNotThrow(() -> bookingService.delete(1L));
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    void testDelete_PastBooking() {
        Booking booking = createBooking(1L, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), createCar(1L));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        HttpStatusException exception = assertThrows(HttpStatusException.class, () -> bookingService.delete(1L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Only future bookings can be deleted for consistancy.", exception.getMessage());
    }

    @Test
    void testForceDelete() {
        Booking booking = createBooking(1L, LocalDate.now(), LocalDate.now().plusDays(1), createCar(1L));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        assertDoesNotThrow(() -> bookingService.forceDelete(1L));
        verify(bookingRepository, times(1)).delete(booking);
    }
}
