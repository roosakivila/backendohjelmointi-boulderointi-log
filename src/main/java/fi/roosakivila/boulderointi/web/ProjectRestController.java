package fi.roosakivila.boulderointi.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.roosakivila.boulderointi.domain.AppUser;
import fi.roosakivila.boulderointi.domain.AppUserRepository;
import fi.roosakivila.boulderointi.domain.Project;
import fi.roosakivila.boulderointi.domain.ProjectRepository;
import jakarta.validation.Valid;

@Controller
public class ProjectRestController {

    private ProjectRepository projectRepository;
    private AppUserRepository appUserRepository;

    public ProjectRestController(ProjectRepository projectRepository, AppUserRepository appUserRepository) {
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
    }

    // Get all projects (users see own, admins see all)
    @GetMapping("/projects")
    public @ResponseBody List<Project> getAllProjects(Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        
        if (currentUser.getRole().equals("ADMIN")) {
            return (List<Project>) projectRepository.findAll();
        } else {
            return projectRepository.findByAppuser_UserId(currentUser.getUserId());
        }
    }

     // Get project by id (ownership check)
    @GetMapping("/projects/{id}")
    public @ResponseBody Optional<Project> getProjectById(@PathVariable("id") Long projectId, Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        Optional<Project> project = projectRepository.findById(projectId);
        
        if (project.isPresent()) {
            Project p = project.get();
            if (!currentUser.getRole().equals("ADMIN") && 
                !p.getAppuser().getUserId().equals(currentUser.getUserId())) {
                throw new IllegalArgumentException("Project not found: " + projectId);
            }
        }
        return project;
    }

    // Uodate project (ownership check)
    @PutMapping("/projects/{id}")
    public @ResponseBody Project updateProject(@PathVariable("id") Long projectId, @Valid @RequestBody Project project, Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
        
        if (!currentUser.getRole().equals("ADMIN") && 
            !existingProject.getAppuser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
        
        existingProject.setStatus(project.getStatus());
        existingProject.setAttempts(project.getAttempts());
        existingProject.setNotes(project.getNotes());
        existingProject.setRoute(project.getRoute());
        
        return projectRepository.save(existingProject);
    }

    // Delete project (ownership check)
    @DeleteMapping("/projects/{id}")
    public @ResponseBody void deleteProject(@PathVariable("id") Long projectId, Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
        
        // Users can only delete their own projects, admins can delete any
        if (!currentUser.getRole().equals("ADMIN") && 
            !project.getAppuser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
        
        projectRepository.deleteById(projectId);
    }

}
