package people.user.adapter.persistence.queries;

import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;
import java.util.UUID;

@NamedNativeQuery(
    name = "usersWithGoogleEmailQuery",
    query = " select u.id id, u.name name, u.email email "
        + " from user u "
        + " where u.email like '%@gmail.%' ",
    resultSetMapping = "usersWithGoogleEmailMapping"
)
@SqlResultSetMapping(
         name = "usersWithGoogleEmailMapping",
         entities = {
             @EntityResult(
                 entityClass = UsersWithGoogleEmailQueryResult.class,
                 fields = {
                     @FieldResult(name = "id", column = "id"),
                     @FieldResult(name = "name", column = "name"),
                     @FieldResult(name = "email", column = "email")
                 }
             )
         }
     )
@Entity
public class UsersWithGoogleEmailQueryResult implements Serializable {

    @Id //siempre necesario, si no, peta
    private UUID id;
    private String name;
    private String email;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
