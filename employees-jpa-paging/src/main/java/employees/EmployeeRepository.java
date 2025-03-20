package employees;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select new employees.EmployeeDto(e.id, e.name, e.personalNumber) from Employee e")
    List<EmployeeDto> findAllDto();

    @Query(value = "select distinct e from Employee e join fetch e.addresses", countQuery = "select count(e) from Employee e")
    Page<Employee> findAllWithAddresses(Pageable pageable);

    @Query(value = "select distinct e from Employee e join fetch e.addresses order by e.name")
    Slice<Employee> findAllSlicesWithAddresses(Pageable pageable);
}
