package ch.juventus.car_rental.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookingCreateDTO {
    @Schema(description = "Start date of the booking", example = "2025-01-08")
    private LocalDate fromDate;

    @Schema(description = "End date of the booking", example = "2025-01-10")
    private LocalDate toDate;

    @Schema(description = "Name of the customer", example = "John Doe")
    private String customerName;

    @Schema(description = "ID of the car to book", example = "123")
    private Long carId;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCar(Long carId) {
        this.carId = carId;
    }
}
