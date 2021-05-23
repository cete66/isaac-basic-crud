package people.user.adapter.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.dto.UserEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(UserAdapter.class);
    private final UserRepository userRepository;

    public UserAdapter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findUserByNameAndEmail(final String name, final String email) {
        return userRepository.findByNameAndEmail(name, email);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(final UUID userId) {
        return userRepository.findById(userId);
    }

    public void delete(final UUID userId) {
        userRepository.deleteById(userId);
    }

    public UUID persist(final UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
        return userEntity.getId();
    }

    public void update(final UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
    }
}
