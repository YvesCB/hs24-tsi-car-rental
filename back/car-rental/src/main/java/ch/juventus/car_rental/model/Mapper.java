package ch.juventus.car_rental.model;

import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static BookingDTO toDto(Booking booking) {
        Long carId = booking.getCar().getId();
        return new BookingDTO(booking.getId(), booking.getFromDate(), booking.getToDate(), booking.getCustomerName(),
                carId);
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
}
