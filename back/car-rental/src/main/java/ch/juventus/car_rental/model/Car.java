package ch.juventus.car_rental.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private int yearOfConstruction;
    private int pricePerDay;

    private boolean automatic;

    @ManyToOne
    @JoinColumn(name = "type_id") // Foreign key column in Car table
    private CarType type;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
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
}
