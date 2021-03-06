package people.user.adapter.persistence;

import javassist.NotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.dto.UserEntity;
import people.user.adapter.persistence.queries.UsersWithGoogleEmailQueryResult;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserAdapter {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    //private final Connection connection;

    public UserAdapter(final UserRepository userRepository,
                       final EntityManager entityManager,
                       final JdbcTemplate jdbcTemplate
                       //final Connection connection
    ) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
        //this.connection = connection;
    }

    /*@Transactional(rollbackOn = SQLException.class)
    public void insert(final UserEntity userEntity) throws SQLException {

        try (final PreparedStatement ps = connection.prepareStatement(
            " insert into user (id, name, email) "
            + " values (?, ?, ?) ")) {
            ps.setString(1, userEntity.getId().toString());
            ps.setString(2, userEntity.getName());
            ps.setString(3, userEntity.getEmail());

            ps.executeUpdate();
        }

    }*/

    public List<UserEntity> emFindUsersWithGoogleEmail() {
        return entityManager.createNativeQuery(
            " select u.id id, u.name name, u.email email "
            + " from user u "
            + " where u.email like '%@gmail.%' ", UserEntity.class).getResultList();
    }

    public List<UsersWithGoogleEmailQueryResult> emNamedFindUsersWithGoogleEmail() {
        return entityManager.createNamedQuery(
            "usersWithGoogleEmailQuery", UsersWithGoogleEmailQueryResult.class)
            .getResultList();
    }

    public void jdbcSaveUser(final UserEntity userEntity) {
        jdbcTemplate.update(" insert into user (id, name, email) "
            + " values (?, ?, ?) ", userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public void saveUser(final UserEntity userEntity) {
        entityManager.createNativeQuery(
            " insert into user (id, name, email) "
                + " values (:id, :name, :email) ")
            .setParameter("id", userEntity.getId())
            .setParameter("name", userEntity.getName())
            .setParameter("email", userEntity.getEmail())
            .executeUpdate();
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

    public UserEntity update(final UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    public void emPersist(final UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    public UserEntity emUpdate(final UserEntity userEntity) throws NotFoundException {
        if (entityManager.find(UserEntity.class, userEntity.getId()) != null) {
            return entityManager.merge(userEntity);
        } else {
            throw new NotFoundException(String.format("User with id %s not found", userEntity.getId()));
        }
    }

    public Optional<UserEntity> emFindById(final UUID userId) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, userId));
    }

    public List<UserEntity> emFindAllUsers() {
        return entityManager.createQuery(
            "select ue From UserEntity ue", UserEntity.class)
            .getResultList();
    }


}
