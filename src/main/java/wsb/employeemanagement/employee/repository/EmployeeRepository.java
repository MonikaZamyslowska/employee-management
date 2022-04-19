package wsb.employeemanagement.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRoles(Role role);
    Employee findEmployeeByUsername(String username);
    Employee findById(long employeeId);

    void removeById(long employeeId);
}
