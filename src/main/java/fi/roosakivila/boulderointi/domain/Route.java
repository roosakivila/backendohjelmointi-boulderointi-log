package fi.roosakivila.boulderointi.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    private String name;
    private String grade;

    @ManyToOne
    @JsonIgnoreProperties("gyms")
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
