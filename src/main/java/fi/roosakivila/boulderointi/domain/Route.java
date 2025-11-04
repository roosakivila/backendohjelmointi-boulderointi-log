package fi.roosakivila.boulderointi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Route name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Grade is required")
    @Pattern(regexp = "^(?:[4-9][a-cA-C]?\\+?|[Vv][0-9])$", message = "Grade must be in format: 4-9a/b/c+ (e.g., 6A, 6b+) or V-scale (e.g., V6, V7)")
    private String grade;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    @JoinColumn(name = "gymId")
    private Gym gym;

    public Route() {

    }

    public Route(String name, String grade, Gym gym) {
        this.name = name;
        this.grade = grade;
        this.gym = gym;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "Route [routeId=" + routeId + ", name=" + name + ", grade=" + grade + ", gym=" + gym + "]";
    }

}
