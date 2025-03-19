package employees;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAllDto();
    }

    public EmployeeDto save(EmployeeDto employee) {
        // MapStruct can be used here
        var entity = new Employee(employee.name(), employee.personalNumber());
        employeeRepository.save(entity);
        return new EmployeeDto(entity.getId(), entity.getName(), entity.getPersonalNumber());
    }
}
