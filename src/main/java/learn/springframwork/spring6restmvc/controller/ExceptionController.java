package learn.springframwork.spring6restmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Global Exception Handler for all Controller defined
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {

        System.out.println("In the Exception Handler");

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
