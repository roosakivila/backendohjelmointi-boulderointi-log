package fi.roosakivila.boulderointi.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymId;

    @NotBlank(message = "Gym name is required")
    @Size(min = 1, max = 100, message = "Gym name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(min = 1, max = 30, message = "City name must be between 1 and 30 characters")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gym")
    @JsonIgnoreProperties("gym")
    private List<Route> routes;

    public Gym() {

    }

    public Gym(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Gym [gymId=" + gymId + ", name=" + name + ", city=" + city + "]";
    }

}
