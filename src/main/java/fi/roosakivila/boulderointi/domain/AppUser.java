package fi.roosakivila.boulderointi.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 10, message = "Username must be between 3 and 10 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appuser")
    private List<Project> projects;

    public AppUser() {

    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.passwordHash = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", password=" + passwordHash + ", role=" + role
                + "]";
    }

}
