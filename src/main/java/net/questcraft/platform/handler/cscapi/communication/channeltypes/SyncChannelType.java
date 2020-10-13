package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpChannelHandler;

public final class SyncChannelType {
    public static final ChannelType<HttpChannelHandler> CLIENT_HTTP = () -> HttpChannelHandler.class;
    public static final ChannelType<HttpChannelHandler> SERVER_HTTP  = () -> HttpChannelHandler.class;
}
