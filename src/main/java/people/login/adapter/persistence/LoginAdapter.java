package people.login.adapter.persistence;

import org.springframework.stereotype.Component;
import people.login.adapter.persistence.dto.LoginEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class LoginAdapter {

    private final EntityManager entityManager;
    private final LoginRepository loginRepository;

    public LoginAdapter(final EntityManager entityManager,
                        final LoginRepository loginRepository) {
        this.entityManager = entityManager;
        this.loginRepository = loginRepository;
    }

    public Optional<LoginEntity> findByEmail(final String email) {
        return loginRepository.findByEmail(email);
    }

    public void persist(final LoginEntity loginEntity) {
        loginRepository.save(loginEntity);
    }
}
