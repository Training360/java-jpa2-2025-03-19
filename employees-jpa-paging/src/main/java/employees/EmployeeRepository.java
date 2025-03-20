package employees;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select new employees.EmployeeDto(e.id, e.name, e.personalNumber) from Employee e")
    List<EmployeeDto> findAllDto();

    @EntityGraph(
            attributePaths = "addresses"
    )
    @Query("select e from Employee e")
    List<Employee> findAllWithAddresses();
}
