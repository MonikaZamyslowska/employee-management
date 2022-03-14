package wsb.employeemanagement.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.service.EmployeeService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public EmployeeDto createEmployee(@RequestBody final EmployeeDto employeeDto) {
        return null;
    }

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return null;
    }

    @GetMapping(value = "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable long employeeId) {
        return null;
    }

    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody final EmployeeDto employeeDto) {
        return null;
    }

    @DeleteMapping(value = "/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {

    }
}
