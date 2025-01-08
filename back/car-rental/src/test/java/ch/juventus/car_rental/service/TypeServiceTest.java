package ch.juventus.car_rental.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import ch.juventus.car_rental.model.Car;
import ch.juventus.car_rental.model.CarType;
import ch.juventus.car_rental.repository.CarRepository;
import ch.juventus.car_rental.repository.TypeRepository;
import jakarta.persistence.EntityNotFoundException;

class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private TypeService typeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<CarType> types = new ArrayList<>();
        var carType = new CarType();
        carType.setName("SUV");
        carType.setDescription("Sports Utility Vehicle");
        types.add(carType);
        when(typeRepository.findAll()).thenReturn(types);

        List<CarType> result = typeService.findAll();

        assertEquals(1, result.size());
        assertEquals("SUV", result.get(0).getName());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        var type = new CarType();
        type.setName("SUV");
        type.setDescription("Sports Utility Vehicle");
        when(typeRepository.findById(1L)).thenReturn(Optional.of(type));

        CarType result = typeService.findById(1L);

        assertNotNull(result);
        assertEquals("SUV", result.getName());
        verify(typeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(typeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> typeService.findById(1L));
        verify(typeRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate_Success() {
        var type = new CarType();
        type.setName("SUV");
        type.setDescription("Sports Utility Vehicle");
        when(typeRepository.findAll()).thenReturn(new ArrayList<>());
        var returntype = new CarType();
        returntype.setName("SUV");
        returntype.setDescription("Sports Utility Vehicle");
        when(typeRepository.save(type)).thenReturn(returntype);

        CarType result = typeService.create(type);

        assertNotNull(result);
        assertEquals("SUV", result.getName());
        verify(typeRepository, times(1)).findAll();
        verify(typeRepository, times(1)).save(type);
    }

    @Test
    void testCreate_DuplicateType() {
        var type = new CarType();
        type.setName("SUV");
        type.setDescription("Sports Utility Vehicle");
        var existingType = new CarType();
        existingType.setName("SUV");
        existingType.setDescription("Sports Utility Vehicle");
        when(typeRepository.findAll()).thenReturn(List.of(existingType));

        HttpStatusException exception = assertThrows(HttpStatusException.class, () -> typeService.create(type));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Name and Description cannot all overlap with existing type.", exception.getMessage());
        verify(typeRepository, times(1)).findAll();
        verify(typeRepository, never()).save(any());
    }

    @Test
    void testUpdate_Success() {
        var existingType = new CarType();
        existingType.setName("SUV");
        existingType.setDescription("Sports Utility Vehicle");
        var updatedDetails = new CarType();
        existingType.setName("Sedan");
        existingType.setDescription("Compact Car");
        var sedan = new CarType();
        sedan.setName("Sedan");
        sedan.setDescription("Compact Car");
        when(typeRepository.findById(1L)).thenReturn(Optional.of(existingType));
        when(typeRepository.save(existingType)).thenReturn(sedan);

        CarType result = typeService.update(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Sedan", result.getName());
        verify(typeRepository, times(1)).findById(1L);
        verify(typeRepository, times(1)).save(existingType);
    }

    @Test
    void testDelete_Success() {
        var type = new CarType();
        type.setName("SUV");
        type.setDescription("Sports Utility Vehicle");
        when(typeRepository.findById(1L)).thenReturn(Optional.of(type));
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> typeService.delete(1L));
        verify(typeRepository, times(1)).delete(type);
    }

    @Test
    void testDelete_TypeAssignedToCar() {
        var type = new CarType();
        type.setId(1L);
        type.setName("SUV");
        type.setDescription("Sports Utility Vehicle");
        Car car = new Car();
        car.setType(type);
        when(typeRepository.findById(1L)).thenReturn(Optional.of(type));
        when(carRepository.findAll()).thenReturn(List.of(car));

        HttpStatusException exception = assertThrows(HttpStatusException.class, () -> typeService.delete(1L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Only types that are assigned to no cars can be deleted.", exception.getMessage());
        verify(typeRepository, never()).delete(any());
    }
}
