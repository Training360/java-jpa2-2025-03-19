package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = "delete from employees")
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void save() {
        var employee = new Employee("John Doe", "12345");
        employeeRepository.save(employee);
        var id = employee.getId();

        var loaded = employeeRepository.findById(id).get();

        loaded = employeeRepository.findById(id).get();

        var employees = employeeRepository.findAllDto();

        employees = employeeRepository.findAllDto();

    }

}
