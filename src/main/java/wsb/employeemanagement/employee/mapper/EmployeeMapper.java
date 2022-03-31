package wsb.employeemanagement.employee.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeDto mapEmployeeToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGrade(),
                employee.getCapacity(),
                employee.getSupervisor(),
                employee.getSkillList(),
                employee.getRoles(),
                employee.getTasks(),
                employee.getTaskRequests(),
                employee.getOwnerProject()
        );
    }

    public Employee mapDtoToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getUsername(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getGrade(),
                employeeDto.getCapacity(),
                employeeDto.getSupervisor(),
                employeeDto.getSkillList(),
                employeeDto.getRoles(),
                employeeDto.getTasks(),
                employeeDto.getTaskRequests(),
                employeeDto.getOwnerProject()
        );
    }

    public List<EmployeeDto> mapEmployeeListToDto(List<Employee> employees) {
        return employees.stream()
                .map(this::mapEmployeeToDto)
                .collect(Collectors.toList());
    }
}
