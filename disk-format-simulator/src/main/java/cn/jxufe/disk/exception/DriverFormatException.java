package cn.jxufe.disk.exception;

public class DriverFormatException extends RuntimeException {

    public DriverFormatException(String message) {
        super(message);
    }

    public DriverFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}