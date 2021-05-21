package people.user.adapter.persistence;

import org.springframework.stereotype.Component;
import people.user.adapter.persistence.dto.UserEntity;
import people.user.adapter.rest.dto.UserRequest;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserAdapter {

    private final UserRepository userRepository;

    public UserAdapter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findUserByNameAndEmail(final String name, final String email) {
        return userRepository.findByNameAndEmail(name, email);
    }

    public UUID save(final UserRequest userRequest) {
        final var userId = UUID.randomUUID();
        final var entity = new UserEntity(userId, userRequest.getName(), userRequest.getEmail());
        userRepository.save(entity);
        userRepository.flush();
        return userId;
    }
}
