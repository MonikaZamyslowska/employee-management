package wsb.employeemanagement.employee.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wsb.employeemanagement.exception.EmployeeManagerRuntimeException;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class KeycloakException extends EmployeeManagerRuntimeException {
    public KeycloakException(String message) {
        super(message);
    }
}
