package employees;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @QueryHints(
            @QueryHint(name="org.hibernate.cacheable", value = "true")
    )
    @Query("select new employees.EmployeeDto(e.id, e.name, e.personalNumber) from Employee e")
    List<EmployeeDto> findAllDto();
}
