package uk.tw.energy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchSmartMeterExtsit extends RuntimeException{

    public NoSuchSmartMeterExtsit(String message){
        super(message);
    }
}
