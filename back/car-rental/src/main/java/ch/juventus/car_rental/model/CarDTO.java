package ch.juventus.car_rental.model;

public class CarDTO {

    private Long id;

    private String name;
    private String brand;
    private int yearOfConstruction;
    private int pricePerDay;

    private boolean automatic;
    private boolean active;

    private CarType type;

    public CarDTO(Long id, String name, String brand, int yearOfConstruction, int pricePerDay, boolean automatic,
            boolean active, CarType type) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.yearOfConstruction = yearOfConstruction;
        this.pricePerDay = pricePerDay;
        this.automatic = automatic;
        this.active = active;
        this.type = type;
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
