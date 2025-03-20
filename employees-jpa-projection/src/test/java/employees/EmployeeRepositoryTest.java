package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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


        List<PlainEmployeeWithCityDto> employees = employeeRepository.findAllWithAddresses();
        employees.forEach(System.out::println);

//        Map<Long, EmployeeCitiesDto> employeesWithCities = new HashMap<>();
//        for (PlainEmployeeWithCityDto employee : employees) {
//            EmployeeCitiesDto found = employeesWithCities.get(employee.employeeId());
//            if (found == null) {
//                found = new EmployeeCitiesDto(employee.employeeId(), employee.employeeName(), new ArrayList<>());
//                employeesWithCities.put(employee.employeeId(), found);
//            }
//            else {
//                found.cities().add(employee.cityName());
//            }
//        }

        List<EmployeeCitiesDto> employeesWithCities = new ArrayList<>(employees.stream()
                .collect(Collectors.toMap(
                        PlainEmployeeWithCityDto::employeeId,
                        dto -> new EmployeeCitiesDto(dto.employeeId(), dto.employeeName(), new ArrayList<>(List.of(dto.cityName()))),
                        (existing, replacement) -> {
                            existing.cities().add(replacement.cities().get(0));
                            return existing;
                        }
                )).values());


//        List<EmployeeCitiesDto> employeesWithCities = employees.stream()
//                .map(e -> new EmployeeCitiesDto(e.getId(),
//                        e.getName(),
//                        e.getAddresses().stream().map(Address::getCity).toList()))
//                .toList();

        assertThat(employeesWithCities.getFirst().name()).startsWith("John Doe");
        assertThat(employeesWithCities.getLast().cities()).containsExactlyInAnyOrder("Budapest", "Pécs");
    }
}
