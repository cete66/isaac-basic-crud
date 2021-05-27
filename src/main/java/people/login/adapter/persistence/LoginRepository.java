package people.login.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import people.login.adapter.persistence.dto.LoginEntity;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {

    Optional<LoginEntity> findByEmail(final String email);
}
