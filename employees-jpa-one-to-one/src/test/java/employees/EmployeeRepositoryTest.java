package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = {"delete from parking_places", "delete from employees"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    void save() {
        var employee = new Employee("John Doe", "12345");
        employee.setParkingPlace(new ParkingPlace("55555"));
        employeeRepository.save(employee);

        var employees = employeeRepository.findAll();

        assertThat(employees)
                .extracting(Employee::getName)
                .containsExactly("John Doe");
    }

    @Test
    void remove() {
        var employee = new Employee("John Doe", "12345", "lorem ipsum");
//        var employee = new Employee("John Doe", "12345");
        employee.setParkingPlace(new ParkingPlace("55555"));
        employeeRepository.save(employee);
        var id = employee.getId();

        transactionTemplate.executeWithoutResult(status -> {
                    Employee loaded = employeeRepository.findById(id).orElseThrow();
//                    loaded.removeParkingPlace();
//            System.out.println(Persistence.getPersistenceUtil().isLoaded(employee));
                });




    }


}
