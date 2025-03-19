package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "delete from employees")
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void save() {
        employeeRepository.save(new Employee("John Doe", "12345"));

        var employees = employeeRepository.findAll();

        assertThat(employees)
                .extracting(Employee::getName)
                .containsExactly("John Doe");
    }

    @Test
    void findAllDto() {
        employeeRepository.save(new Employee("John Doe", "12345"));
        employeeRepository.save(new Employee("Jane Doe", "123456"));

        var employees = employeeRepository.findAllDto();

        assertThat(employees)
                .extracting(EmployeeDto::name)
                .containsExactly("John Doe", "Jane Doe");
    }
}
