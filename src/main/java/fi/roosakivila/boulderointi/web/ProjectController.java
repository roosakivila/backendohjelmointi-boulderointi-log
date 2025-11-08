package fi.roosakivila.boulderointi.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.roosakivila.boulderointi.domain.AppUser;
import fi.roosakivila.boulderointi.domain.AppUserRepository;
import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.Project;
import fi.roosakivila.boulderointi.domain.ProjectRepository;
import fi.roosakivila.boulderointi.domain.Route;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import fi.roosakivila.boulderointi.domain.Status;
import jakarta.validation.Valid;

@Controller
public class ProjectController {

    private ProjectRepository projectRepository;
    private RouteRepository routeRepository;
    private GymRepository gymRepository;
    private AppUserRepository appUserRepository;

    public ProjectController(ProjectRepository projectRepository,
            RouteRepository routeRepository, GymRepository gymRepository, AppUserRepository appUserRepository) {
        this.projectRepository = projectRepository;
        this.routeRepository = routeRepository;
        this.gymRepository = gymRepository;
        this.appUserRepository = appUserRepository;
    }

    // GET projects
    @GetMapping("/projectlist")
    public String projectList(Model model, java.security.Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());

        if (currentUser.getRole().equals("ADMIN")) {
            // Admin sees all projects
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("isAdmin", true);
        } else {
            // Regular users see only their own projects
            model.addAttribute("projects", projectRepository.findByAppuser_UserId(currentUser.getUserId()));
            model.addAttribute("isAdmin", false);
        }

        return "projectlist"; // projectlist.html
    }

    // Add project
    @RequestMapping("/addproject")
    public String addProject(@RequestParam(value = "gymId", required = false) Long gymId, Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("route", new Route());
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("gyms", gymRepository.findAll());

        // Filter routes by gym if gymId is provided
        if (gymId != null) {
            model.addAttribute("routes", routeRepository.findByGym_GymId(gymId));
            model.addAttribute("selectedGymId", gymId);
        } else {
            model.addAttribute("routes", routeRepository.findAll());
            model.addAttribute("selectedGymId", null);
        }

        return "addproject"; // addproject.html
    }

    // Save project
    @PostMapping("/saveproject")
    public String saveProject(@Valid Project project, BindingResult bindingResult, java.security.Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());

        // If editing existing project, check ownership
        if (project.getProjectId() != null) {
            Project existingProject = projectRepository.findById(project.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + project.getProjectId()));

            // Users can only edit their own projects, admins can edit any
            if (!currentUser.getRole().equals("ADMIN") &&
                    !existingProject.getAppuser().getUserId().equals(currentUser.getUserId())) {
                return "redirect:projectlist";
            }
        }

        project.setAppuser(currentUser);
        project.setDateModified(LocalDateTime.now());
        if (bindingResult.hasErrors()) {
            if (project.getProjectId() != null) {
                return "editproject";
            }
            return "addproject";
        } else {
            projectRepository.save(project);
            return "redirect:projectlist";
        }
    }

    // Delete project
    @GetMapping("/deleteproject/{id}")
    public String deleteProject(@PathVariable("id") Long id, Model model, java.security.Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));

        // Users can only delete their own projects, admins can delete any
        if (!currentUser.getRole().equals("ADMIN") &&
                !project.getAppuser().getUserId().equals(currentUser.getUserId())) {
            return "redirect:../projectlist";
        }

        projectRepository.deleteById(id);
        return "redirect:../projectlist";
    }

    // Edit project
    @GetMapping("/editproject/{id}")
    public String modifyProject(@PathVariable("id") Long id, Model model, java.security.Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName());
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));

        // Users can only edit their own projects, admins can edit any
        if (!currentUser.getRole().equals("ADMIN") &&
                !project.getAppuser().getUserId().equals(currentUser.getUserId())) {
            return "redirect:../projectlist";
        }

        model.addAttribute("project", project);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("route", new Route());
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("gyms", gymRepository.findAll());
        return "editproject"; // editproject.html
    }

}
