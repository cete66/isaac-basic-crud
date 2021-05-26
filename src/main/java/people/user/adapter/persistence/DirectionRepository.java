package people.user.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import people.user.adapter.persistence.dto.DirectionEntity;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DirectionRepository extends JpaRepository<DirectionEntity, Long> {

}
