package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = "delete from employees")
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    EntityManager entityManager;

    @Test
    void save() {
        var employee = employeeRepository.save(new Employee("John Doe", "12345"));
        var id = employee.getId();

        transactionTemplate.executeWithoutResult(status -> {
            var loaded = employeeRepository.findById(id).orElseThrow();
            loaded.setName("John Doe 2");
            System.out.println(entityManager.contains(loaded));
            System.out.println(entityManager.contains(employee));
            // commit
        });

        transactionTemplate.executeWithoutResult(status -> {
            var loaded = employeeRepository.findById(id).orElseThrow();
            loaded.setName("John Doe 3");
        });
    }
}
