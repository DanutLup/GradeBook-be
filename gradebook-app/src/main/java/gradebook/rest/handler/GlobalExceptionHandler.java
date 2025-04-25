package gradebook.rest.handler;

import gradebook.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserException.class)
  ResponseEntity<Object> handleUserException(UserException ex) {
    return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
  }
}
