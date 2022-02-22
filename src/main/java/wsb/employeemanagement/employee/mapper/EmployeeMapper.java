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
                employee.getUser(),
                employee.getGrade(),
                employee.getCapacity(),
                employee.getSupervisor(),
                employee.getSkillList(),
                employee.getRoles(),
                employee.getTasks(),
                employee.getOwnerProject()
        );
    }

    public Employee mapDtoToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getUser(),
                employeeDto.getGrade(),
                employeeDto.getCapacity(),
                employeeDto.getSupervisor(),
                employeeDto.getSkillList(),
                employeeDto.getRoles(),
                employeeDto.getTasks(),
                employeeDto.getOwnerProject()
        );
    }

    public List<EmployeeDto> mapEmployeeListToDto(List<Employee> employees) {
        return employees.stream()
                .map(employee -> mapEmployeeToDto(employee))
                .collect(Collectors.toList());
    }
}
