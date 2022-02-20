package wsb.employeemanagement.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
