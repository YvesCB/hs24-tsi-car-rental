package ch.juventus.car_rental.model;

import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static BookingDTO toDto(Booking booking) {
        CarDTO carDto = toDto(booking.getCar());
        return new BookingDTO(booking.getId(), booking.getFromDate(), booking.getToDate(), booking.getCustomerName(),
                carDto);
    }

    public static Booking toEntity(BookingDTO bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setFromDate(bookingDto.getFromDate());
        booking.setToDate(bookingDto.getToDate());
        booking.setCustomerName(bookingDto.getCustomerName());
        booking.setCar(null);

        return booking;
    }

    public static Booking toEntity(BookingCreateDTO bookingDto) {
        Booking booking = new Booking();
        booking.setId(null);
        booking.setFromDate(bookingDto.getFromDate());
        booking.setToDate(bookingDto.getToDate());
        booking.setCustomerName(bookingDto.getCustomerName());
        booking.setCar(null);

        return booking;
    }

    public static CarDTO toDto(Car car) {
        return new CarDTO(car.getId(), car.getName(), car.getBrand(), car.getYearOfConstruction(), car.getPricePerDay(),
                car.isAutomatic(), car.isActive(), car.getType());
    }
}
