package people.user.adapter.persistence.extractor;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import people.user.adapter.persistence.dto.DirectionEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DirectionEntityQueryExtractor implements ResultSetExtractor<DirectionEntity> {

    @Override
    public DirectionEntity extractData(@NotNull final ResultSet rs) throws SQLException, DataAccessException {
        DirectionEntity directionEntity = null;

        if (rs.next()) {
            directionEntity = new DirectionEntity(
                rs.getLong("id"),
                rs.getString("street_name"),
                rs.getInt("number"),
                rs.getString("zip_code"));
        }

        return directionEntity;
    }
}
