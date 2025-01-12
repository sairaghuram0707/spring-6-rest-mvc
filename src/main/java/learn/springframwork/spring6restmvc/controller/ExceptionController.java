package learn.springframwork.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Global Exception Handler for all Controller defined */
/* Redundant now as we are using @ResponseStatus in ExceptionClass(Handling all the controller at one place) */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity handleNotFoundException() {

        System.out.println("In the Exception Handler");

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {

        // Formatting the Error for beeter understanding.
        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                   Map<String, String> errorMap = new HashMap<>();
                   errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                   return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {

        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cv = (ConstraintViolationException) exception.getCause().getCause();

            List errors = cv.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                       Map<String, String> errorMap = new HashMap<>();
                       errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                       return errorMap;
                    }).toList();
            return responseEntity.body(errors);
        }

        return responseEntity.build();

    }

}
