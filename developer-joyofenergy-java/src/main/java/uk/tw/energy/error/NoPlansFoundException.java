package uk.tw.energy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPlansFoundException extends RuntimeException{
    public NoPlansFoundException(String message){
        super(message);
    }

    public NoPlansFoundException(String msg, Throwable msg2){
        super(msg,msg2);
    }
}

