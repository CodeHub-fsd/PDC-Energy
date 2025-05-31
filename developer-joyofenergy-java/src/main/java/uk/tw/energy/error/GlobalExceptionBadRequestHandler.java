package uk.tw.energy.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionBadRequestHandler {

    @ExceptionHandler(Global400Error.class)
    public ResponseEntity<Map<String,String>> error(Global400Error ex){
        Map<String, String> map=new HashMap<>();
        map.put("error",ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }
}
