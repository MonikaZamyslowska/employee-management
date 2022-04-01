package wsb.employeemanagement.employee.keycloak;

import wsb.employeemanagement.employee.domain.Employee;

public interface KeycloakRepository {
    boolean createUser(Employee employee) throws KeycloakException;

    boolean updateUser(Employee employee) throws KeycloakException;
}
