package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.communication.websocket.WebSocketHandler;

public enum ChannelType {
    WEB_SOCKET(0, WebSocketHandler.class),
    HTTP(1, null),
    HTTPS(2, null);

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
