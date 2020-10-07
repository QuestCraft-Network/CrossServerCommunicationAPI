package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.client.WebSocketClientHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.server.WebSocketServerHandler;

public enum  DefaultChannelTypes implements ChannelType {
    /**
     * Websocket ChannelTypes
     */
    CLIENT_WS(0, WebSocketClientHandler.class),
    SERVER_WS(1, WebSocketServerHandler.class),

    /**
     * Transfer Control Protocol ChannelTypes
     *
     * Not implemented
     */
    CLIENT_TCP(2, null),
    SERVER_TCP(3, null);

    /**
     * int id for specified ChannelType
     */
    private int id;

    /**
     * Specified ChannelHandler class for each ChannelType
     */
    private Class<? extends ChannelHandler> clsType;

    DefaultChannelTypes(int id, Class<? extends ChannelHandler> clsType) {
        this.id = id;
        this.clsType = clsType;
    }

    /**
     * Getter for member variable id
     *
     * @return int ID of ChannelType
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Getter for member variable clsType
     *
     * @return Class<? extends ChannelHandler> of ChannelType
     */
    @Override
    public Class<? extends ChannelHandler> getClsType() {
        return clsType;
    }
}
