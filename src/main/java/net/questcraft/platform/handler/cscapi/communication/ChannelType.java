package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.communication.websocket.client.WebSocketClientHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.server.WebSocketServerHandler;

public enum ChannelType {
    CLIENT_WS(0, WebSocketClientHandler.class),
    SERVER_WS(1, WebSocketServerHandler.class),

    HTTP(2, null),
    HTTPS(3, null);

    private int id;
    private Class<? extends ChannelHandler> clsType;

    ChannelType(int id, Class<? extends ChannelHandler> clsType) {
        this.id = id;
        this.clsType = clsType;
    }

    public int getId() {
        return id;
    }

    public Class<? extends ChannelHandler> getClsType() {
        return clsType;
    }
}
