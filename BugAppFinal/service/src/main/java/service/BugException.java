package service;

public class BugException extends Exception{
    public BugException() {}

    public BugException(String message) {
        super(message);
    }

    public BugException(String message, Throwable cause) {
        super(message, cause);
    }
}
