package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.client.WebSocketClientHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.server.WebSocketServerHandler;

public final class SyncChannelType {
    public static final ChannelType<WebSocketClientHandler> CLIENT_HTTP = () -> null;
    public static final ChannelType<WebSocketServerHandler> SERVER_HTTP  = () -> null;

}
