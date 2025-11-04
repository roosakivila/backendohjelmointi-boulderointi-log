package fi.roosakivila.boulderointi.web;

import fi.roosakivila.boulderointi.domain.AppUser;
import fi.roosakivila.boulderointi.domain.AppUserRepository;
import jakarta.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppUserController {

    private AppUserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AppUserController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "signup"; // signup.html
    }

    @PostMapping("/signup")
    public String signup(@Valid AppUser user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already taken");
            return "signup";
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/login";
    }
}