package fi.roosakivila.boulderointi.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.Route;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    private RouteRepository routeRepository;
    private GymRepository gymRepository;

    public RouteController(RouteRepository routeRepository, GymRepository gymRepository) {
        this.routeRepository = routeRepository;
        this.gymRepository = gymRepository;
    }

    // Redirect home to routelist
    @GetMapping("/")
    public String home() {
        return "redirect:/routelist";
    }

    // Get routelist
    @GetMapping("/routelist")
    public String routeList(Model model) {
        model.addAttribute("routes", routeRepository.findAll());
        return "routelist"; // routelist.html
    }

    // Add route
    @RequestMapping("/addroute")
    public String addRoute(@RequestParam(value = "redirect", required = false) String redirectUrl, Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("gyms", gymRepository.findAll());
        if (redirectUrl != null) {
            model.addAttribute("redirectUrl", redirectUrl);
        }
        return "addroute"; // addroute.html
    }

    // Save route
    @PostMapping("/saveroute")
    public String saveRoute(Route route, @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
        routeRepository.save(route);
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/routelist";
    }

    // Delete route
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRoute(@PathVariable("id") Long id, Model model) {
        routeRepository.deleteById(id);
        return "redirect:../routelist";
    }

    //  Edit route
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String modifyRoute(@PathVariable("id") Long id, Model model) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid route Id:" + id));
        model.addAttribute("route", route);
        model.addAttribute("gyms", gymRepository.findAll());
        return "editroute"; // editroute.html
    }
}
