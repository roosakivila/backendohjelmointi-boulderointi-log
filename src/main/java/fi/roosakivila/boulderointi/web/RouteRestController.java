package fi.roosakivila.boulderointi.web;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.roosakivila.boulderointi.domain.Route;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import jakarta.validation.Valid;

@Controller
public class RouteRestController {

    private RouteRepository routeRepository;

    public RouteRestController(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    // Get all routes
    @GetMapping("/routes")
    public @ResponseBody List<Route> getAllRoutes(){
        return (List<Route>) routeRepository.findAll();
    }

    //Get route by id
    @GetMapping("/routes/{id}")
    public @ResponseBody Optional<Route> getRouteById(@PathVariable("id") Long routeId){
        return routeRepository.findById(routeId);
    }

    //Add route (authorized)
    @PostMapping("/routes")
    @PreAuthorize("hasAuthority('ADMIN')") // muuta tämä!
    public @ResponseBody Route addRoute(@Valid @RequestBody Route route) {
        return routeRepository.save(route);
    }

    //Update route (authorized)
    @PutMapping("routes/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody Route updateRoute(@PathVariable("id") Long routeId, @Valid @RequestBody Route route) {
        Route existingRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found: " + routeId));
        existingRoute.setName(route.getName());
        existingRoute.setGrade(route.getGrade());
        existingRoute.setGym(route.getGym());
        return routeRepository.save(existingRoute);
    }

    //Delete route (admin)
    @DeleteMapping("routes/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody void deleteRoute(@PathVariable("id") Long routeId) {
        routeRepository.deleteById(routeId);
    }

}
