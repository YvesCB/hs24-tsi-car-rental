package ch.juventus.car_rental.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Car id", example = "24")
    private Long id;

    @Schema(description = "The name of the car", example = "Model S")
    private String name;
    @Schema(description = "The brand of the car", example = "Tesla")
    private String brand;
    @Schema(description = "The year of construction of the car", example = "2015")
    private int yearOfConstruction;
    @Schema(description = "The price per day of the car", example = "4000")
    private int pricePerDay;

    @Schema(description = "Gearbox automaitic", example = "true")
    private boolean automatic;
    @Schema(description = "Car active", example = "true")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "type_id") // Foreign key column in Car table
    @Schema(description = "Type of car")
    private CarType type;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Bookings of the car")
    private List<Booking> bookings = new ArrayList<>();

    public boolean compare(Car other) {
        if (other == null) {
            return false;
        }

        boolean isNameEqual = this.name != null && this.name.equals(other.name);
        boolean isTypeEqual = this.type != null && other.type != null
                && this.type.getId() != null && this.type.getId().equals(other.type.getId());
        boolean isBrandEqual = this.brand != null && this.brand.equals(other.brand);

        return isNameEqual && isTypeEqual && isBrandEqual;
    }

    public boolean availableDuring(LocalDate start, LocalDate end) {
        for (Booking booking : bookings) {
            if (booking.getFromDate().isBefore(end) && booking.getToDate().isAfter(start))
                return false;
        }
        return true;
    }

    public boolean hasCurrentOrFutureBooking() {
        LocalDate today = LocalDate.now();
        for (Booking booking : bookings) {
            if (booking.getToDate().isAfter(today) || booking.getToDate().isEqual(today)
                    || booking.getFromDate().isAfter(today) || booking.getFromDate().isEqual(today))
                return true;
        }
        return false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    // Add a getter and setter for the bookings field
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
