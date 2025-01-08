package ch.juventus.car_rental.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookingCreateDTO {
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String customerName;
    private Long carId;

    public BookingCreateDTO(Long id, LocalDate fromDate, LocalDate toDate, String customerName, Long carId) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customerName = customerName;
        this.carId = carId;
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

    public Long getCarId() {
        return carId;
    }

    public void setCar(Long carId) {
        this.carId = carId;
    }
}
