package wsb.employeemanagement.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;
import wsb.employeemanagement.validation.Username;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeService.createEmployee(employeeMapper.mapDtoToEmployee(employeeDto)));
        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/keycloack")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public EmployeeDto updateEmployeeKeycloack(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.mapEmployeeToDto(employeeService.updateEmployeeKeycloack(employeeMapper.mapDtoToEmployee(employeeDto)));
    }

    @GetMapping
    @RolesAllowed({"ROLE_ADMIN"})
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.mapEmployeeListToDto(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public EmployeeDto getEmployeeById(@PathVariable long employeeId) {
        return employeeMapper.mapEmployeeToDto(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping("username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public EmployeeDto getEmployeeByUsername(@PathVariable @Username String username) {
        return employeeMapper.mapEmployeeToDto(employeeService.getEmployeeByUsername(username));
    }

    @GetMapping("/supervisor/{supervisorId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<EmployeeDto> getEmployeesBySupervisorId(@PathVariable long supervisorId) {
        return employeeMapper.mapEmployeeListToDto(employeeService.getEmployeeBySupervisor(supervisorId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public EmployeeDto updateEmployee(@RequestBody final EmployeeDto employeeDto) {
        return employeeMapper.mapEmployeeToDto(employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto)));
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_ADMIN"})
    public void deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
