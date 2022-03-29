package wsb.employeemanagement.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;

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
    public ResponseEntity createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeMapper.mapEmployeeToDto(employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto))));
        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping
    //(params = {"!employeeId"})
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.mapEmployeeListToDto(employeeService.getAllEmployees());
    }

    @GetMapping(value = "{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable long employeeId) {
        return employeeMapper.mapEmployeeToDto(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping(value = "supervisor/{supervisorId}")
    public List<EmployeeDto> getEmployeesBySupervisorId(@PathVariable long supervisorId) {
        return employeeMapper.mapEmployeeListToDto(employeeService.getEmployeeBySupervisor(supervisorId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@RequestBody final EmployeeDto employeeDto) {
        return employeeMapper.mapEmployeeToDto(employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto)));
    }

    @DeleteMapping(value = "{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
