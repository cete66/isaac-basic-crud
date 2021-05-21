package people.user.adapter.rest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import people.user.UserService;
import people.user.adapter.rest.dto.UserRequest;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(final UserRequest userRequest) {
        return "add-user";
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
}
