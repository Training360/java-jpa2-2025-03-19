package employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    @Query("select new employees.EmployeeDto(e.id, e.name, e.personalNumber) from Employee e")
    List<EmployeeDto> findAllBy();

    <T> List<T> findAllBy(Class<T> clazz);

    Optional<EmployeeDto> findByPersonalNumber(String personalNumber);

    @Query("select distinct new employees.PlainEmployeeWithCityDto(e.id, e.name, a.city) from Employee e join e.addresses a")
    List<PlainEmployeeWithCityDto> findAllWithAddresses();

}
