package wsb.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This User is not found in the database.")
public class UserNotFoundException extends RuntimeException {
}
