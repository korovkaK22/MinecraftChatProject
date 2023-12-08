package exceptions;

public class ServerDoNotInitializeException extends Exception{
    public ServerDoNotInitializeException() {
        super();
    }


    public ServerDoNotInitializeException(String message) {
        super(message);
    }


    public ServerDoNotInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

}
