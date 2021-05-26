package people.user.adapter.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.dto.DirectionEntity;
import people.user.adapter.persistence.extractor.DirectionEntityQueryExtractor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
public class DirectionAdapter {

    private final EntityManager entityManager;
    private final DirectionRepository directionRepository;
    private final JdbcTemplate jdbcTemplate;
    private final DirectionEntityQueryExtractor directionEntityQueryExtractor;

    public DirectionAdapter(final EntityManager entityManager,
                            final DirectionRepository directionRepository,
                            final JdbcTemplate jdbcTemplate,
                            final DirectionEntityQueryExtractor directionEntityQueryExtractor) {
        this.entityManager = entityManager;
        this.directionRepository = directionRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.directionEntityQueryExtractor = directionEntityQueryExtractor;
    }

    public Optional<DirectionEntity> jdbcFindById(final long userId) {
        return Optional.ofNullable(jdbcTemplate.query(
            "select id, name, email from user where id = ? ", directionEntityQueryExtractor, userId));
    }

    public List<DirectionEntity> repoFindAll() {
        return directionRepository.findAll();
    }

    public void jdbcInsert(final DirectionEntity directionEntity) {
        jdbcTemplate.update(" insert into direction (id, street, number, zip_code) " +
            "values (?, ?, ?, ?) ",
            directionEntity.getId(), directionEntity.getStreetName(),
            directionEntity.getNumber(), directionEntity.getZipCode());
    }

    public void emSaveDirection(final DirectionEntity directionEntity) {
        entityManager.persist(directionEntity);
    }

    public void repoSaveDirection(final DirectionEntity directionEntity) {
        directionRepository.saveAndFlush(directionEntity);
    }

    public void emMerge(final DirectionEntity directionEntity) {
        entityManager.merge(directionEntity);
    }

    public Optional<DirectionEntity> emFindByData(final DirectionEntity directionEntity) {
        final List<DirectionEntity> result = entityManager.createQuery(
            " select de "
            + " From DirectionEntity de "
            + " Where de.streetName = :street "
            + " and de.number = :number "
            + " and de.zipCode = :zipCode ", DirectionEntity.class)
            .setParameter("street", directionEntity.getStreetName())
            .setParameter("number", directionEntity.getNumber())
            .setParameter("zipCode", directionEntity.getZipCode())
            .getResultList(); //if we call .getSingleResult and there isn't any -> throws unhandled exception (thanks jpa)

        if (result != null && ! result.isEmpty()) {
            return Optional.ofNullable(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    public Optional<DirectionEntity> emFindById(final long id) {
        return Optional.ofNullable(entityManager.find(DirectionEntity.class, id));
    }
}
