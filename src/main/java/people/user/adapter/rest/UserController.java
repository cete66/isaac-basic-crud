package people.user.adapter.rest;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import people.user.UserService;
import people.user.adapter.persistence.dto.UserEntity;
import people.user.adapter.rest.dto.UserRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Controller
@Validated
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(final UserRequest userRequest) {
        return "add-user";
    }

    @GetMapping("/index")
    public String showUserList(final Model model) {
        LOG.info(" /index llamado -> {}", model);
        final List<UserEntity> users = userService.findAll();
        model.addAttribute("users", userService.findAll());
        reset(model);

        return "index";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid final UserRequest userRequest,
                          final BindingResult result,
                          final Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userService.processNewUser(userRequest);

        return "redirect:/index"; //llama al path del controller /index
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") @NotBlank final String id, final Model model) {
        final var user = userService.findById(UUID.fromString(id));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUserName(@PathVariable("id") final String id, @Valid final UserRequest userRequest,
                             BindingResult result, Model model) throws NotFoundException {
        if (result.hasErrors()) {
            return "update-user";
        }
        userService.update(id, userRequest);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        userService.delete(UUID.fromString(id));
        return "redirect:/index";
    }

    private void reset(final Model model) {
        model.addAttribute("registerRequest", null);
        if (model.containsAttribute("loggeduser")) {
            model.addAttribute("loggeduser", null);
        }
    }
}
