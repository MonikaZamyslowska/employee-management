package wsb.employeemanagement.employee.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"Admin"})
    public ResponseEntity createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeMapper.mapEmployeeToDto(employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto))));
        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping
    @RolesAllowed({"Admin"})
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.mapEmployeeListToDto(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    @RolesAllowed({"User", "Moderator", "Admin"})
    public EmployeeDto getEmployeeById(@PathVariable long employeeId) {
        return employeeMapper.mapEmployeeToDto(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping("/supervisor/{supervisorId}")
    @RolesAllowed({"Moderator"})
    public List<EmployeeDto> getEmployeesBySupervisorId(@PathVariable long supervisorId) {
        return employeeMapper.mapEmployeeListToDto(employeeService.getEmployeeBySupervisor(supervisorId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"User", "Moderator", "Admin"})
    public EmployeeDto updateEmployee(@RequestBody final EmployeeDto employeeDto, KeycloakAuthenticationToken token) {
        if (token != null && (token.getAccount().getRoles().contains("Admin") ||
                token.getAccount().getPrincipal().getName().equals(employeeDto.getUsername()))) {
            employeeMapper.mapEmployeeToDto(employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto)));
        }
        return null;
    }

    @DeleteMapping("/{employeeId}")
    @RolesAllowed({"User", "Moderator", "Admin"})
    public void deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
