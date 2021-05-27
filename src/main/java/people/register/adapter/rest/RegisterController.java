package people.register.adapter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import people.register.RegisterService;
import people.register.adapter.rest.dto.RegisterRequest;

@Controller
@Validated
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);
    private final RegisterService registerService;

    public RegisterController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/registry")
    public String loadRegisterForm(final RegisterRequest registerRequest,
                                   final Model model) {
        LOG.info(" /registry llamado -> {}", model);
        model.addAttribute("registerRequest", registerRequest);
        return "register";
    }

    @PostMapping("/register")
    public String register(final RegisterRequest registerRequest, final Model model) {
        LOG.info(" /register llamado -> {}", model);
        final RegisterRequest registered = registerService.register(registerRequest);

        model.addAttribute("registerRequest", registered);

        return "redirect:/index";
    }
}
