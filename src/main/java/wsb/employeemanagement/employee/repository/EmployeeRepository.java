package wsb.employeemanagement.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByUsername(String username);
    void removeById(long employeeId);
}
