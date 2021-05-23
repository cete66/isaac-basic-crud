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
import people.user.adapter.rest.dto.UserRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
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
        final var users = userService.findAll();
        LOG.info("Users list -> {}", Arrays.toString(users.toArray()));
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid final UserRequest userRequest,
                          final BindingResult result,
                          final Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        final var userId = userService.processNewUser(userRequest);
        //TODO RETURN THIS UUID?
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") @NotBlank final String id, final Model model) {
        final var user = userService.findById(UUID.fromString(id))
            .orElseThrow(() -> new IllegalArgumentException("User with name " + id + " not found"));

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
}
