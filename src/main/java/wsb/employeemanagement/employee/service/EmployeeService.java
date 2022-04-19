package wsb.employeemanagement.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.employee.keycloak.KeycloakException;
import wsb.employeemanagement.employee.keycloak.KeycloakService;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;
import wsb.employeemanagement.exception.EmployeeNotFoundException;
import wsb.employeemanagement.skill.domain.Skill;

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

    @Transactional
    public void updateEmployeeKeycloack(Employee employee) {
        if (!keycloakService.updateUser(employee)) {
            throw new KeycloakException("Could not update user in keycloack");
        }
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployeeSkills(long employeeId, Skill skill) {
        Employee employee = employeeRepository.findById(employeeId);
        List<Skill> employeeSkills = employee.getSkillList();
        employeeSkills.add(skill);
        employee.setSkillList(employeeSkills);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public List<Employee> getEmployeeByRole(Role role) {
        return employeeRepository.findByRoles(role);
    }

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }

    @Transactional
    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        employeeRepository.removeById(employeeId);
        if (!keycloakService.deleteUser(employee)) {
            throw new KeycloakException("Could not update user in keycloack");
        }
    }

    private void verifyEmployeeDoesNotExist(String username) {
        if (employeeRepository.findEmployeeByUsername(username) != null) {
            throw new EmployeeAlreadyExistsException("Employee with username " + username + " already exists");
        }
    }
}
