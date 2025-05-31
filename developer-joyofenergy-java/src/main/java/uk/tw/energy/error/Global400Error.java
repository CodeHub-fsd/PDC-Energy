package uk.tw.energy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Global400Error extends RuntimeException {
    public Global400Error(String messgae) {
        super(messgae);
    }

    public Global400Error(String message, Throwable msg2){
        super(message,msg2);
    }
}
