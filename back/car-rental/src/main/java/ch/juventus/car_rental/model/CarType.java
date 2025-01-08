package ch.juventus.car_rental.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The id of the CarType", example = "33")
    private Long id;

    @Schema(description = "The name of the type", example = "SUV")
    private String name;
    @Schema(description = "The description of the type", example = "Sports Utility Vehicle")
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
