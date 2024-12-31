package ch.juventus.car_rental.model;

import jakarta.persistence.*;

/**
 * Car
 */

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private int yearOfConstruction;
    private boolean automatic;

    @ManyToOne
    @JoinColumn(name = "type_id") // Foreign key column in Car table
    private Type type;

    /*
     * public Car(Long id, String name, String brand, int yearOfConstruction,
     * boolean automatic, String type) {
     * this.id = id;
     * this.name = name;
     * this.brand = brand;
     * this.yearOfConstruction = yearOfConstruction;
     * this.automatic = automatic;
     * this.type = type;
     * }
     */

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
