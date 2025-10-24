package fi.roosakivila.boulderointi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int attempts;
    private String notes;

    public Project() {

    }

    public Project(Status status, int attempts, String notes) {
        this.status = status;
        this.attempts = attempts;
        this.notes = notes;
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

    @Override
    public String toString() {
        return "Project [projectId=" + projectId + ", status=" + status + ", attempts=" + attempts + ", notes=" + notes
                + "]";
    }

}
