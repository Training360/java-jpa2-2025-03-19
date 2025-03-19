package employees;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
public record EmployeeDto(Long id, String name, String personalNumber) implements Serializable {
}