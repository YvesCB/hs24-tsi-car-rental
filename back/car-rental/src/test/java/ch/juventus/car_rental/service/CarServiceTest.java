package ch.juventus.car_rental.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.juventus.car_rental.exceptions.HttpStatusException;
import ch.juventus.car_rental.model.Booking;
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private TypeService typeService;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Car> mockCars = List.of(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(mockCars);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Car mockCar = new Car();
        when(carRepository.findById(1L)).thenReturn(Optional.of(mockCar));

        Car result = carService.findById(1L);

        assertNotNull(result);
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_EntityNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.findById(1L));
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate_Success() {
        CarType mockType = new CarType();
        mockType.setId(1L);
        Car mockCar = new Car();
        mockCar.setType(mockType);
        mockCar.setName("TestCar");
        mockCar.setBrand("TestBrand");

        when(typeService.findById(1L)).thenReturn(mockType);
        when(carRepository.findAll()).thenReturn(new ArrayList<>());
        when(carRepository.save(mockCar)).thenReturn(mockCar);

        Car result = carService.create(mockCar);

        assertNotNull(result);
        verify(carRepository, times(1)).save(mockCar);
    }

    @Test
    void testCreate_NameAndBrandEmpty() {
        Car mockCar = new Car();
        mockCar.setName("");
        mockCar.setBrand("");

        HttpStatusException exception = assertThrows(HttpStatusException.class, () -> carService.create(mockCar));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Name and Brand cannot be empty", exception.getMessage());
    }

    @Test
    void testDuplicateCheck() {
        CarType suvType = new CarType();
        suvType.setId(1L);

        Car car1 = new Car();
        car1.setName("Car A");
        car1.setBrand("Brand A");
        car1.setType(suvType);

        Car car2 = new Car();
        car2.setName("Car A");
        car2.setBrand("Brand A");
        car2.setType(suvType);

        assertTrue(car1.compare(car2)); // Should return true
    }

    @Test
    void testUpdate_Success() {
        Car existingCar = new Car();
        CarType type = new CarType();
        type.setId(1L);
        Car mockCarDetails = new Car();
        mockCarDetails.setName("UpdatedName");
        mockCarDetails.setBrand("UpdatedBrand");
        mockCarDetails.setType(type);

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        when(typeService.findById(1L)).thenReturn(type);
        when(carRepository.save(existingCar)).thenReturn(existingCar);

        Car result = carService.update(1L, mockCarDetails);

        assertEquals("UpdatedName", existingCar.getName());
        assertEquals("UpdatedBrand", existingCar.getBrand());
        assertEquals(type, existingCar.getType());
        verify(carRepository, times(1)).save(existingCar);
    }

    @Test
    void testUpdate_PriceChangeForBookedCar() {
        Car existingCar = new Car();
        existingCar.setBookings(List.of(new Booking())); // Mock bookings

        Car mockCarDetails = new Car();

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> carService.update(1L, mockCarDetails));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Price of cars with prior or future bookings cannot be changed.", exception.getMessage());
    }

    @Test
    void testDelete_CarHasBookings() {
        Car existingCar = new Car();
        existingCar.setBookings(List.of(new Booking())); // Mock bookings

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));

        HttpStatusException exception = assertThrows(HttpStatusException.class, () -> carService.delete(1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("A car with existing bookings cannot be deleted. Deactive it instead.", exception.getMessage());
    }

    @Test
    void testDelete_Success() {
        Car existingCar = new Car();

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));

        carService.delete(1L);

        verify(carRepository, times(1)).delete(existingCar);
    }
}
