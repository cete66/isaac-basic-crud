package people.user.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import people.user.adapter.persistence.dto.UserEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest //to load default configuration to test jpa code (repositories, entityManager, etc)
public class UserAdapterIT {

    @Autowired
    private UserAdapter userAdapter;

    @Test
    public void testAutowired() {
        assertNotNull(userAdapter);
    }

    @Test
    public void testEmFindAllUsers() {
        final var user = new UserEntity(UUID.randomUUID(), "name", "email@gmail.com");
        userAdapter.emPersist(user);
        final var actual = userAdapter.emFindAllUsers();

        assertEquals(1, actual.size());
        assertEquals(user, actual.get(0));
    }

    @Test
    public void testEmFindUsersWithGoogleEmail() {
        final var user = new UserEntity(UUID.randomUUID(), "name", "email@gmail.com");
        userAdapter.emPersist(user);
        userAdapter.emPersist(new UserEntity(UUID.randomUUID(), "asd", "asd@asd.com"));
        final var actual = userAdapter.emFindUsersWithGoogleEmail();

        assertEquals(1, actual.size());
        assertEquals(user.getEmail(), actual.get(0).getEmail());
    }
}