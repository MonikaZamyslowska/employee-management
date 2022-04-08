package wsb.employeemanagement.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.keycloak.KeycloakService;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;
import wsb.employeemanagement.exception.EmployeeNotFoundException;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private KeycloakService keycloakService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, KeycloakService keycloakService) {
        this.employeeRepository = employeeRepository;
        this.keycloakService = keycloakService;
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        verifyEmployeeDoesNotExist(employee.getUsername());
        keycloakService.createUser(employee);
        return employeeRepository.save(employee);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeeBySupervisor(long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        return employeeRepository.findBySupervisor(employee);
    }

    public void deleteEmployee(long employeeId) {
        employeeRepository.removeById(employeeId);
    }


    private void verifyEmployeeDoesNotExist(String username) {
        employeeRepository.findEmployeeByUsername(username)
                .orElseThrow(() -> new EmployeeAlreadyExistsException("Employee with username " + username + " already exists"));
    }
}
