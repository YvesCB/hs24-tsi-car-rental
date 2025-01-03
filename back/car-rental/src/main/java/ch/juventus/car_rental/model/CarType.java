package ch.juventus.car_rental.model;

import org.hibernate.resource.transaction.spi.IsolationDelegate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public boolean compare(CarType other) {
        if (other == null) {
            return false;
        }

        boolean isNameEqual = this.name != null && this.name.equals(other.name);
        boolean isDescriptionEqual = this.description != null && other.description != null;

        return isNameEqual && isDescriptionEqual;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
