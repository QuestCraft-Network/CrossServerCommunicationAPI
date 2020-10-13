package net.questcraft.platform.handler.cscapi.communication.sync.http.error;

import net.questcraft.platform.handler.cscapi.annotations.HttpClassID;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;

@HttpClassID("HttpPacketError")
public class HttpPacketError implements HttpPacket {
    private String message;

    public HttpPacketError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
