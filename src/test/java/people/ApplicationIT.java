package people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationIT {

    @Autowired
    private Application application;

    @Test
    public void test() {
        assertNotNull(application);
    }

}