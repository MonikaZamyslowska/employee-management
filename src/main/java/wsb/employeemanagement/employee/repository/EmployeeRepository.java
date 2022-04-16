package wsb.employeemanagement.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySupervisor(Employee employee);

    Employee findEmployeeByUsername(String username);

    void removeById(long employeeId);
}
