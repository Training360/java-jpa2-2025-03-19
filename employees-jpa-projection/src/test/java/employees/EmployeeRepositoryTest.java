package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Sql(statements = {"delete from addresses", "delete from employees"})
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

        var employees = employeeRepository.findAllBy();

        assertThat(employees)
                .extracting(EmployeeDto::name)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    void findByPersonalNumber() {
        employeeRepository.save(new Employee("John Doe", "12345"));
        var employee = employeeRepository.findByPersonalNumber("12345").get();
        assertEquals("John Doe", employee.name());
    }

    @Test
    void findAllEmployeeDto() {
        employeeRepository.save(new Employee("John Doe", "12345"));
        employeeRepository.save(new Employee("Jane Doe", "123456"));

        List<EmployeeDto> employees = employeeRepository.findAllBy(EmployeeDto.class);

        assertThat(employees)
                .extracting(EmployeeDto::name)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    void findAllEmployeeIdAndNameDto() {
        employeeRepository.save(new Employee("John Doe", "12345"));
        employeeRepository.save(new Employee("Jane Doe", "123456"));

        List<EmployeeIdAndNameDto> employees = employeeRepository.findAllBy(EmployeeIdAndNameDto.class);

        assertThat(employees)
                .extracting(EmployeeIdAndNameDto::name)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    void findEmployeesWithCities() {
        IntStream.range(0, 50)
                .forEach(i -> {
                    Employee employee = new Employee("John Doe" + i, "12345" + i);
                    employee.addAddress(new Address("Budapest", "Váci utca 1."));
                    employee.addAddress(new Address("Pécs", "Fő utca 2."));
                    employeeRepository.save(employee);
                });


        List<Employee> employees = employeeRepository.findAllWithAddresses();
        List<EmployeeCitiesDto> employeesWithCities = employees.stream()
                .map(e -> new EmployeeCitiesDto(e.getId(),
                        e.getName(),
                        e.getAddresses().stream().map(Address::getCity).toList()))
                .toList();

        assertThat(employeesWithCities.getFirst().name()).startsWith("John Doe");
        assertThat(employeesWithCities.getLast().cities()).containsExactlyInAnyOrder("Budapest", "Pécs");
    }
}
