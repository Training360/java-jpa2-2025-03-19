package employees;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
public record EmployeeIdAndNameDto(Long id, String name) implements Serializable {
}