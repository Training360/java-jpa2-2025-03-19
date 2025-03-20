package employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select new employees.AddressDto(a.employee.id, a.city) from Address a where a.employee.id in :ids")
    List<AddressDto> findAllAddressDto(List<Long> ids);

    @Query("select new employees.PhoneDto(p.employee.id, p.number) from Phone p where p.number like '1%' and p.employee.id in :ids")
    List<PhoneDto> findAllPhoneDto(List<Long> ids);

    @Query("select new employees.EmployeeDto(e.id, e.name, e.personalNumber) from Employee e where e.name like :namePostfix")
    List<EmployeeDto> findAllEmployeeDto(String namePostfix);

}
