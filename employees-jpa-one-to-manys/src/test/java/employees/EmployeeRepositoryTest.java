package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = {"delete from employees", "delete from addresses", "delete from employees"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void createTestData() {
        for (int i = 0; i < 10; i++) {
            var employee = new Employee("John Doe " + i, "12345" + i);
            employee.addPhone(new Phone("11111"));
            employee.addPhone(new Phone("22222"));
            employee.addAddress(new Address("Budapest"));
            employee.addAddress(new Address("Pécs"));
            employeeRepository.save(employee);
        }
    }

    @Test
    void findAll() {
    }

}
