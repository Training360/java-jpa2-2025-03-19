package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(statements = {"delete from addresses", "delete from employees"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void save() {
        for (int i = 0; i < 50; i++) {
            var employee = new Employee("John Doe " + i, "12345" + i);
            employee.addAddress(new Address("Budapest"));
            employee.addAddress(new Address("Pécs"));
            employeeRepository.save(employee);
        }

//        var employees = employeeRepository.findAll(PageRequest.of(2, 5));
//        var employees = employeeRepository.findAllWithAddresses(PageRequest.of(2, 5));
        var employees = employeeRepository.findAllSlicesWithAddresses(PageRequest.of(2, 5));
        System.out.println(employees.stream().map(Employee::getName).collect(Collectors.joining(", ")));
    }

}
