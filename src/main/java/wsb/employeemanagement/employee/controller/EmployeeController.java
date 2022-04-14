package wsb.employeemanagement.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeAlreadyExistsException;
import wsb.employeemanagement.validation.Username;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import java.util.Arrays;
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

    @PostMapping("/create")
    @RolesAllowed({"ROLE_ADMIN"})
    public String createEmployee(@Valid @ModelAttribute EmployeeDto employeeDto) {
        try {
            ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeeService.createEmployee(employeeMapper.mapDtoToEmployee(employeeDto)));
        } catch (EmployeeAlreadyExistsException e) {
            ResponseEntity.status(400).body(e.getMessage());
        }
        return "redirect:/all";
    }

    @GetMapping("/all")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView getEmployees() {
        ModelAndView modelAndView = new ModelAndView("list-employees");
        modelAndView.addObject("employees", employeeMapper.mapEmployeeListToDto(employeeService.getAllEmployees()));
        return modelAndView;
    }

    @GetMapping("/employeeId/{employeeId}")
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

    @GetMapping("/update/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView updateEmployee(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        Employee employee = employeeService.getEmployeeById(employeeId);
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @DeleteMapping("/delete/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_ADMIN"})
    public String deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/all";
    }

//    for view only

    @GetMapping("/addEmployeeForm")
    public ModelAndView addEmployeeForm() {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        EmployeeDto newEmployee = new EmployeeDto();
        List<Grade> grades = Arrays.asList(Grade.values().clone());
        List<Role> roles = Arrays.asList(Role.values().clone());
        modelAndView.addObject("employee", newEmployee);
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeMapper.mapDtoToEmployee(employeeDto));
        return "redirect:/all";
    }
}
