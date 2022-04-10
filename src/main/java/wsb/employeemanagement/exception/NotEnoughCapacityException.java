package wsb.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "This Employee hasn't enough capacity.")
public class NotEnoughCapacityException extends RuntimeException {
}
