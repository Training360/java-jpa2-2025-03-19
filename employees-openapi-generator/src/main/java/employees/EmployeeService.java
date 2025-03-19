package employees;

import employees.model.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                // MapStruct can be used here
                .map(this::toDto)
                .toList();
    }

    public EmployeeDto save(EmployeeDto employee) {
        // MapStruct can be used here
        var entity = new Employee(employee.getName(), employee.getPersonalNumber());
        employeeRepository.save(entity);
        return toDto(entity);
    }

    private EmployeeDto toDto(Employee employee) {
        var dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPersonalNumber(employee.getPersonalNumber());
        return dto;
    }
}
