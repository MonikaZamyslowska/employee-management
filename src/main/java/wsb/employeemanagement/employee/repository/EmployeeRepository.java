package wsb.employeemanagement.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);
    List<Employee> findBySupervisor(Employee employee);
    void removeById(long employeeId);
}
