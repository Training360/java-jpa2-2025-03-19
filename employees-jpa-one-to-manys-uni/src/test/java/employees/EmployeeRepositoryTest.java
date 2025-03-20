package employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

@SpringBootTest
@Sql(statements = {"delete from phones", "delete from addresses", "delete from employees"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @BeforeEach
    void createTestData() {
        for (int i = 0; i < 20; i++) {
            var employee = new Employee("John Doe " + i, "12345" + i);
            employeeRepository.save(employee);
            phoneRepository.save(new Phone("11111", employee));
            phoneRepository.save(new Phone("22222", employee));
            addressRepository.save(new Address("Budapest", employee));
            addressRepository.save(new Address("Pécs", employee));

        }
    }


    @Test
//@RepeatedTest(5)
//    @Transactional
    void findAllWithProjection() {

        transactionTemplate.executeWithoutResult(status -> {
            long start = System.currentTimeMillis();
            // Open context
            var employees = employeeRepository.findAllEmployeeDto("%3");

            Map<Long, EmployeeDto> employeesMap = employees.stream().collect(Collectors.toMap(
                    EmployeeDto::id, Function.identity()
            ));

            var ids = employees.stream().map(EmployeeDto::id).toList();

            var phones = employeeRepository.findAllPhoneDto(ids);
            for (PhoneDto phoneDto : phones) {
                employeesMap.get(phoneDto.employeeId()).phones().add(phoneDto);
            }

            var addresses = employeeRepository.findAllAddressDto(ids);
            for (AddressDto addressDto : addresses) {
                employeesMap.get(addressDto.employeeId()).addresses().add(addressDto);
            }

            System.out.println(employees);

            long end = System.currentTimeMillis();
            System.out.println("Keves SQL-lel: " + (end - start));

            // Close context
        });
    }

}
