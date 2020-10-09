package net.questcraft.platform.handler.cscapi.communication.sync.http.server;

import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPipeline;

public abstract class HttpServerPipeline extends HttpPipeline {
    public abstract HttpPacket onMessage(HttpPacket packet);
}
