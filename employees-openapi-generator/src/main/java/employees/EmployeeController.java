package employees;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "list employees")
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @PostMapping
    @Operation(summary = "save an employee")
    public EmployeeDto save(@RequestBody EmployeeDto employee) {
        return employeeService.save(employee);
    }
}
