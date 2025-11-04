package fi.roosakivila.boulderointi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private Status status;

    @Min(value = 0, message = "Attempts must be 0 or greater")
    private int attempts;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    @JoinColumn(name = "userId")
    private AppUser appuser;

    @ManyToOne
    @NotNull(message = "Route is required")
    @JsonIgnoreProperties("routes")
    @JoinColumn(name = "routeId")
    private Route route;

    public Project() {

    }

    public Project(Status status, int attempts, String notes, AppUser user, Route route) {
        this.status = status;
        this.attempts = attempts;
        this.notes = notes;
        this.appuser = user;
        this.route = route;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AppUser getAppuser() {
        return appuser;
    }

    public void setAppuser(AppUser user) {
        this.appuser = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Project [projectId=" + projectId + ", status=" + status + ", attempts=" + attempts + ", notes=" + notes
                + ", user=" + appuser + ", route=" + route + "]";
    }

}
