package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "addresses")
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String city;

    private String street;

    @ManyToOne
    private Employee employee;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }
}
