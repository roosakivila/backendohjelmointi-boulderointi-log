package fi.roosakivila.boulderointi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    private RouteRepository routeRepository;
    private GymRepository gymRepository;

    public RouteController(RouteRepository routeRepository, GymRepository gymRepository) {
        this.routeRepository = routeRepository;
        this.gymRepository = gymRepository;
    }

    @GetMapping("/routelist")
    public String routeList(Model model) {
        model.addAttribute("routes", routeRepository.findAll());
        return "routelist"; // routelist.html
    }

}
