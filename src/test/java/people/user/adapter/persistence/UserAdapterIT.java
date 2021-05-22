package people.user.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest //to load default configuration to test jpa code (repositories, entityManager, etc)
public class UserAdapterIT {

    @Autowired
    private UserAdapter userAdapter;

    @Test
    public void test() {
        assertNotNull(userAdapter);
    }
}