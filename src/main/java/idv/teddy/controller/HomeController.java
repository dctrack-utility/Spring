package idv.teddy.controller;

import idv.teddy.payload.RegisterForm;
import idv.teddy.repository.UserDetailsRepository;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Data
public class HomeController {
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    private String hello(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(RegisterForm registerForm) {
        return "registerForm";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid RegisterForm registerForm, Errors errors) {
        if (errors.hasErrors())
            return "registerForm";
        userDetailsRepository.save(registerForm.toUser(passwordEncoder));
        return "redirect:/";
    }
}
