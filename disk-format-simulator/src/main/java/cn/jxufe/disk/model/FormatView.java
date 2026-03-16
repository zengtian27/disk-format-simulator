package cn.jxufe.disk.model;

public class FormatView {

    private final boolean success;
    private final String driverLetter;
    private final String partition;
    private final String formatterClassName;
    private final String message;

    public FormatView(boolean success, String driverLetter, String partition, String formatterClassName,
            String message) {
        this.success = success;
        this.driverLetter = driverLetter;
        this.partition = partition;
        this.formatterClassName = formatterClassName;
        this.message = message;
    }

    public static FormatView success(String driverLetter, String partition, String formatterClassName, String message) {
        return new FormatView(true, driverLetter, partition, formatterClassName, message);
    }

    public static FormatView failure(String driverLetter, String message) {
        return new FormatView(false, driverLetter, "", "", message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDriverLetter() {
        return driverLetter;
    }

    public String getPartition() {
        return partition;
    }

    public String getFormatterClassName() {
        return formatterClassName;
    }

    public String getMessage() {
        return message;
    }
}