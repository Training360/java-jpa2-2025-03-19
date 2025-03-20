package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

// SOHA NE HASZNÁLJUK: @Data
@Getter
@Setter
@Entity
@Table(name = "employees")
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    // Surrogate key
    private Long id;

    private String name;

    // Natural key
    @NaturalId
    private String personalNumber;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ParkingPlace parkingPlace;

    @Basic(fetch = FetchType.LAZY)
    private String cv;

    public void setParkingPlace(ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
        parkingPlace.setEmployee(this);
    }

    public void removeParkingPlace() {
        if (parkingPlace != null) {
            parkingPlace.setEmployee(null);
            this.parkingPlace = null;
        }
    }

    public Employee(String name, String personalNumber) {
        this.name = name;
        this.personalNumber = personalNumber;
    }

    public Employee(String name, String personalNumber, String cv) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.cv = cv;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Employee employee = (Employee) o;
        return getId() != null && Objects.equals(getId(), employee.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}