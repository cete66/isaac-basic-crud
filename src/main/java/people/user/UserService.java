package people.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.UserAdapter;
import people.user.adapter.rest.dto.UserRequest;

import javax.transaction.Transactional;
import java.util.UUID;

@Component
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserAdapter userAdapter;


    public UserService(final UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public UUID processNewUser(final UserRequest userRequest) {
        final var userOpt = userAdapter.findUserByNameAndEmail(
            userRequest.getName(), userRequest.getEmail());

        if (userOpt.isEmpty()) {
            return userAdapter.save(userRequest);
        } else {
            LOG.info("User {} already exists in the system.", userRequest);
            return userOpt.get().getId();
        }
    }
}
