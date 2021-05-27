package people.login.adapter.rest;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import people.login.LoginService;
import people.login.adapter.rest.dto.LoginRequest;

@Controller
@Validated
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String login(final LoginRequest loginRequest, final Model model) throws NotFoundException {
        LOG.info(" /login llamado -> {}", model);
        loginService.login(loginRequest);

        model.addAttribute("loggeduser", loginRequest);

        return "redirect:/index";
    }

    @GetMapping("/loadlogin")
    public String loadLoginForm(final LoginRequest loginRequest, final Model model) {
        LOG.info(" /loadlogin llamado -> {}", model);
        model.addAttribute("loginRequest", loginRequest);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(final Model model) {
        LOG.info(" /logout llamado -> {}", model);
        model.addAttribute("loggeduser", null);
        return "redirect:/index";
    }
}
