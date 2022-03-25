package wsb.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This Task is not found in the database.")
public class TaskNotFoundException extends RuntimeException {
}
