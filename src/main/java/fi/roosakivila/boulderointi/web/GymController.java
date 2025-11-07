package fi.roosakivila.boulderointi.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import fi.roosakivila.boulderointi.domain.Gym;
import fi.roosakivila.boulderointi.domain.GymRepository;
import jakarta.validation.Valid;

@Controller
public class GymController {

    private GymRepository gymRepository;

    public GymController(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    // Get gymlist
    @GetMapping("/gymlist")
    public String gymList(Model model) {
        model.addAttribute("gyms", gymRepository.findAll());
        return "gymlist"; // gymlist.html
    }

    // Add gym 
    @GetMapping("/addgym")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addGym(Model model) {
        model.addAttribute("gym", new Gym());
        return "addgym";
    }

    // Save gym
    @PostMapping("/savegym")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveGym(@Valid @ModelAttribute("gym") Gym gym, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addgym";
        }
        gymRepository.save(gym);
        return "redirect:gymlist";
    }

    // Delete gym
    @GetMapping("/deletegym/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteGym(@PathVariable("id") Long id, Model model) {
        gymRepository.deleteById(id);
        return "redirect:../gymlist";
    }

    //  Edit gym
    @GetMapping("/editgym/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String modifyGym(@PathVariable("id") Long id, Model model) {
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid gym Id:" + id));
        model.addAttribute("gym", gym);
        return "editgym"; // editgym.html
    }
}
