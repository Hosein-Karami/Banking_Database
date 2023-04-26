package Exceptions;

import lombok.Getter;

public class FatherException extends RuntimeException{

    @Getter
    private final String exceptionMessage;

    protected FatherException(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }

}
