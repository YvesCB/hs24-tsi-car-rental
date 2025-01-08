package ch.juventus.car_rental.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the Booking", example = "11")
    private Long id;

    @Schema(description = "The start date of the booking", example = "2025-01-01")
    private LocalDate fromDate;

    @Schema(description = "The end date of the booking", example = "2025-01-02")
    private LocalDate toDate;

    @Schema(description = "The name of the customer that booked the car", example = "John Doe")
    private String customerName;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @JsonBackReference
    private Car car;

    // Getters and setters
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
