package people.user.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import people.DbHelper;
import people.user.adapter.persistence.dto.DirectionEntity;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
@Transactional
public class DirectionAdapterIT {

    private static final Logger LOG = LoggerFactory.getLogger(DirectionAdapterIT.class);

    @Autowired
    private DirectionAdapter adapter;
    @Autowired
    private DbHelper dbHelper;


    @Test
    public void testInsertAutoGen() {
        final DirectionEntity entity = new DirectionEntity("calle las primas", 1, "07500");

        adapter.emSaveDirection(entity);

        final DirectionEntity actual = adapter.emFindByData(entity).orElseThrow();

        assertNotEquals(0L, actual.getId());
        assertEquals(entity.getStreetName(), actual.getStreetName());
        assertEquals(entity.getNumber(), actual.getNumber());
        assertEquals(entity.getZipCode(), actual.getZipCode());
    }

    @Test
    public void testFinAll() {
        final DirectionEntity entity = new DirectionEntity(1L, "calle las primas", 1, "07500");

        dbHelper.jdbcDirectionInsert(entity);

        final DirectionEntity actual = adapter.repoFindAll().get(0);

        assertNotEquals(0L, actual.getId());
        assertEquals(entity.getStreetName(), actual.getStreetName());
        assertEquals(entity.getNumber(), actual.getNumber());
        assertEquals(entity.getZipCode(), actual.getZipCode());
    }
}