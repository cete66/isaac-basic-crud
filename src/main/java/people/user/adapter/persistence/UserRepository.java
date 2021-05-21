package people.user.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import people.user.adapter.persistence.dto.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByNameAndEmail(final String name, final String email);
}
