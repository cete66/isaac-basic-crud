package people.user.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import people.user.adapter.persistence.dto.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByNameAndEmail(final String name, final String email);

    @Modifying
    @Query("update From UserEntity ue set ue.name = :name, ue.email = :email where ue.id = :id")
    void updateNameAndEmailById(final String name, final String email, final UUID id);
}
