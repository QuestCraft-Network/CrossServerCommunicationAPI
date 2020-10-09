package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.async.websocket.client.WebSocketClientHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.server.WebSocketServerHandler;

public final class AsyncChannelType {
    public static final ChannelType<WebSocketClientHandler> CLIENT_WS = () -> WebSocketClientHandler.class;
    public static final ChannelType<WebSocketServerHandler> SERVER_WS = () -> WebSocketServerHandler.class;

    public static final ChannelType<WebSocketServerHandler> SERVER_TCP = () -> null;
    public static final ChannelType<WebSocketServerHandler> CLIENT_TCP = () -> null;

}
