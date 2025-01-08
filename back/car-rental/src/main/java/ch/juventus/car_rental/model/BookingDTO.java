package ch.juventus.car_rental.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookingDTO {
    @Schema(description = "The id of the Booking", example = "102")
    private Long id;

    @Schema(description = "The start date of the booking", example = "2025-01-01")
    private LocalDate fromDate;

    @Schema(description = "The end date of the booking", example = "2025-01-02")
    private LocalDate toDate;

    @Schema(description = "The name of the customer that booked the car", example = "John Doe")
    private String customerName;
    private CarDTO carDto;

    public BookingDTO(Long id, LocalDate fromDate, LocalDate toDate, String customerName, CarDTO carDto) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customerName = customerName;
        this.carDto = carDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CarDTO getCar() {
        return carDto;
    }

    public void setCar(CarDTO car) {
        this.carDto = car;
    }
}
