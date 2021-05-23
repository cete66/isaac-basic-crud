package people.user;

import javassist.NotFoundException;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.UserAdapter;
import people.user.adapter.persistence.dto.UserEntity;
import people.user.adapter.rest.dto.UserRequest;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class UserService {

    private final UserAdapter userAdapter;

    public UserService(final UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public UUID processNewUser(final UserRequest userRequest) {
        final var userOpt = userAdapter.findUserByNameAndEmail(
            userRequest.getName(), userRequest.getEmail());

        if (userOpt.isEmpty()) {
            return userAdapter.persist(
                new UserEntity(UUID.randomUUID(), userRequest.getName(), userRequest.getEmail()));
        } else {
            throw new InvalidParameterException(
                String.format("User %s already exists in the system.", userRequest));
        }
    }

    public List<UserEntity> findAll() {
        return userAdapter.findAll();
    }

    public UserEntity findById(final UUID userId) {
        return userAdapter.findById(userId).orElseThrow();
    }

    public void delete(final UUID userId) {
        userAdapter.delete(userId);
    }

    public void update(final String id, final UserRequest userRequest) throws NotFoundException {
        final var user = findById(UUID.fromString(id));

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        userAdapter.update(user);
    }
}
