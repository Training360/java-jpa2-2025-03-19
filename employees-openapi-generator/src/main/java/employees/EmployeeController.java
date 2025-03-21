package employees;

import employees.api.EmployeeControllerApi;
import employees.model.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeControllerApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Override
    public ResponseEntity<EmployeeDto> save(EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.save(employeeDto));
    }
}
