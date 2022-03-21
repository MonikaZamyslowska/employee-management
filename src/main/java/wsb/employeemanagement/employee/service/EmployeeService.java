package wsb.employeemanagement.employee.service;

import org.springframework.stereotype.Service;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.EmployeeNotFoundException;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeeBySupervisor(long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        return employeeRepository.findBySupervisor(employee);
    }

    public void deleteEmployee(long employeeId) {
        employeeRepository.removeById(employeeId);
    }
}
