package fi.roosakivila.boulderointi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.Project;
import fi.roosakivila.boulderointi.domain.ProjectRepository;
import fi.roosakivila.boulderointi.domain.Route;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import fi.roosakivila.boulderointi.domain.Status;

@Controller
public class ProjectController {

    private ProjectRepository projectRepository;
    private RouteRepository routeRepository;
    private GymRepository gymRepository;

    public ProjectController(ProjectRepository projectRepository,
            RouteRepository routeRepository, GymRepository gymRepository) {
        this.projectRepository = projectRepository;
        this.routeRepository = routeRepository;
        this.gymRepository = gymRepository;
    }

    // GET projects
    @GetMapping("/projectlist")
    public String projectList(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projectlist"; // projectlist.html
    }

    // Add project
    @RequestMapping("/addproject")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("route", new Route());
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("gyms", gymRepository.findAll());
        return "addproject"; // addproject.html
    }

    // Save project
    @PostMapping("/saveproject")
    public String saveProject(Project project) {
        projectRepository.save(project);
        return "redirect:projectlist";
    }

}
