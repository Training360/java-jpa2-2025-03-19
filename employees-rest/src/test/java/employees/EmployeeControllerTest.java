package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = "delete from employees")
class EmployeeControllerTest {

    @Autowired
    EmployeeController employeeController;

    @Test
    void testListEmployees() {
        employeeController.save(new EmployeeDto(null, "John Doe", "12345"));

        var employees = employeeController.findAll();
        assertThat(employees)
                .extracting(EmployeeDto::name)
                .contains("John Doe");
    }
}
