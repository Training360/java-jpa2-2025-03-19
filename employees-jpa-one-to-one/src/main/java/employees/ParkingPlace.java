package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parking_places")
@NoArgsConstructor
public class ParkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String number;

    @OneToOne
    @MapsId
    private Employee employee;

    public ParkingPlace(String number) {
        this.number = number;
    }
}