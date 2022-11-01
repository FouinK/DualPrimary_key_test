package dualKey.dual.exception;

public class CustomException extends RuntimeException {
    private String message;

    protected CustomException() {
    }

    public CustomException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
