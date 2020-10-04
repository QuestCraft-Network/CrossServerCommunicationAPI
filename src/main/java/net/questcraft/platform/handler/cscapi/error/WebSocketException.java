package net.questcraft.platform.handler.cscapi.error;

public class WebSocketException extends CSCException {
    public WebSocketException(Throwable t) {
        super(t.getMessage());
    }
}
