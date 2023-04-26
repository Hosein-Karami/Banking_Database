package Exceptions;

public class FatherException extends RuntimeException{

    private String exceptionMessage;

    protected FatherException(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage(){
        return exceptionMessage;
    }

}
