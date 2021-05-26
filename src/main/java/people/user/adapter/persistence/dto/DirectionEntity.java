package people.user.adapter.persistence.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "direction")
public class DirectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "number")
    private int number;

    @Column(name = "zip_code")
    private String zipCode;

    public DirectionEntity() {
        //deserialize
    }

    public DirectionEntity(final String streetName, final int number, final String zipCode) {
        this.streetName = streetName;
        this.number = number;
        this.zipCode = zipCode;
    }

    public DirectionEntity(final long id, final String streetName,
                           final int number, final String zipCode) {
        this.id = id;
        this.streetName = streetName;
        this.number = number;
        this.zipCode = zipCode;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DirectionEntity directionEntity = (DirectionEntity) o;
        return getId() == directionEntity.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Direction{" +
            "id=" + id +
            ", streetName='" + streetName + '\'' +
            ", number=" + number +
            ", zipCode='" + zipCode + '\'' +
            '}';
    }
}
