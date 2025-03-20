package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "phones")
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String number;

    @ManyToOne
    private Employee employee;

    public Phone(String number) {
        this.number = number;
    }

    public Phone(String number, Employee employee) {
        this.number = number;
        this.employee = employee;
    }
}