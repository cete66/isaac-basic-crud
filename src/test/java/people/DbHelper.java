package people;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.DirectionRepository;
import people.user.adapter.persistence.UserRepository;
import people.user.adapter.persistence.dto.DirectionEntity;
import people.user.adapter.persistence.dto.UserEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Component
@Transactional
public class DbHelper {

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final DirectionRepository directionRepository;

    public DbHelper(final EntityManager entityManager,
                    final JdbcTemplate jdbcTemplate,
                    final UserRepository userRepository,
                    final DirectionRepository directionRepository) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.directionRepository = directionRepository;
    }

    @Transactional(TxType.REQUIRES_NEW)
    public void emSaveDirection(final DirectionEntity directionEntity) {
        entityManager.persist(directionEntity);
    }

    @Transactional(TxType.REQUIRES_NEW)
    public void repoSaveDirection(final DirectionEntity directionEntity) {
        directionRepository.saveAndFlush(directionEntity);
    }

    @Transactional(TxType.REQUIRES_NEW)
    public void jdbcDirectionInsert(final DirectionEntity directionEntity) {
        jdbcTemplate.update(" insert into direction (id, street_name, number, zip_code) " +
                "values (?, ?, ?, ?) ",
            directionEntity.getId(), directionEntity.getStreetName(),
            directionEntity.getNumber(), directionEntity.getZipCode());
    }

    public void emDirectionMerge(final DirectionEntity directionEntity) {
        entityManager.merge(directionEntity);
    }

    public void jdbcSaveUser(final UserEntity userEntity) {
        jdbcTemplate.update(" insert into user (id, name, email) "
            + " values (?, ?, ?) ", userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}
