package uk.tw.energy.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPlansFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoPlansFoundException(NoPlansFoundException ex){
        Map<String, String> errorResponse=new HashMap<>();
        errorResponse.put("error",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
