package ch.juventus.car_rental.model;

import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static BookingDTO toDto(Booking booking) {
        Long carId = booking.getCar().getId();
        return new BookingDTO(booking.getId(), booking.getFromDate(), booking.getToDate(), booking.getCustomerName(),
                carId);
    }
}
