package net.questcraft.platform.handler.cscapi.error;

public abstract class CSCException extends Throwable {
    private String message;

    public CSCException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
