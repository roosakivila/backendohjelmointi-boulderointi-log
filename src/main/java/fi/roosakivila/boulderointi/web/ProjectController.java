package fi.roosakivila.boulderointi.web;

import org.springframework.stereotype.Controller;

import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.ProjectRepository;
import fi.roosakivila.boulderointi.domain.RouteRepository;

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

}
