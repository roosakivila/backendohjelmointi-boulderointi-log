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

import fi.roosakivila.boulderointi.domain.Gym;
import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.Route;
import jakarta.validation.Valid;

@Controller
public class GymRestController {

    private GymRepository gymRepository;

    public GymRestController(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    // List all gym
    @GetMapping("/gyms")
    public @ResponseBody List<Gym> getAllGyms() {
        return (List<Gym>) gymRepository.findAll();
    }

    // Get gym by id
    @GetMapping("/gyms/{id}")
    public @ResponseBody Optional<Gym> getGymById(@PathVariable("id") Long gymId) {
        return gymRepository.findById(gymId);
    }

    // Add gym (admin)
    @PostMapping("/gyms")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody Gym addGym(@Valid @RequestBody Gym gym) {
        return gymRepository.save(gym);
    }

    // Update gym (admin)
    @PutMapping("/gyms/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody Gym updateGym(@PathVariable("id") Long gymId, @Valid @RequestBody Gym gym) {
        Gym existingGym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found: " + gymId));
        existingGym.setName(gym.getName());
        existingGym.setCity(gym.getCity());
        return gymRepository.save(existingGym);
    }

    // Delete gym (admin)
    @DeleteMapping("/gyms/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody void deleteGym(@PathVariable("id") Long gymId) {
        gymRepository.deleteById(gymId);
    }

    // Get all routes for a gym
    @GetMapping("/gyms/{id}/routes")
    public @ResponseBody List<Route> getRoutesForGym(@PathVariable("id") Long gymId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found: " + gymId));
        return gym.getRoutes();
    }

}
