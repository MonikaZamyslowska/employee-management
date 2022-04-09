package wsb.employeemanagement.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.keycloak.KeycloakService;
import wsb.employeemanagement.employee.mapper.EmployeeMapper;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.skill.domain.dto.SkillDto;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wsb.employeemanagement.skill.domain.SkillLevel.BEGINNER;

public class EmployeeServiceTest {

    private static final String FRODO = "Frodo";
    private static final Long FRODO_ID = 1L;
    private static final Employee FRODO_ENTITY = employee(FRODO_ID, FRODO);
    private static final EmployeeDto FRODO_DTO = new EmployeeDto(FRODO_ID, FRODO);
    private static final String JAVA = "JAVA";

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private KeycloakService keycloakService;

    @BeforeEach
    void setupEmployeeServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    private static Employee employee(Long id, String firstName) {
        return Employee.builder()
                .id(id)
                .firstName(firstName)
                .build();
    }

    @Test
    void addEmployeeTest() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(FRODO);
        employeeDto.setRoles(singletonList(Role.EMPLOYEE));

        when(employeeMapper.mapDtoToEmployee(employeeDto)).thenReturn(FRODO_ENTITY);
        when(employeeMapper.mapEmployeeToDto(FRODO_ENTITY)).thenReturn(FRODO_DTO);
        when(employeeRepository.save(FRODO_ENTITY)).thenReturn(FRODO_ENTITY);
        when(keycloakService.createUser(employeeMapper.mapDtoToEmployee(employeeDto))).thenReturn(true);

        Employee result = employeeService.createEmployee(employeeMapper.mapDtoToEmployee(employeeDto));

        assertThat(result)
                .isSameAs(FRODO_ENTITY);

        verify(keycloakService).createUser(employeeMapper.mapDtoToEmployee(employeeDto));
    }
}
