package employees;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Employee}
 */
public record EmployeeDto(Long id,
                          @Schema(description = "Employee's name", example = "John Doe")
                          String name, String personalNumber,
                          List<AddressDto> addresses,
                          List<PhoneDto> phones
                          ) implements Serializable {
}