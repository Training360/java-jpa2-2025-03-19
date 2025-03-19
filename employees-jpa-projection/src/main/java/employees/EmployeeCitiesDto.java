package employees;

import java.util.List;

public record EmployeeCitiesDto(Long id, String name, List<String> cities) {
}
