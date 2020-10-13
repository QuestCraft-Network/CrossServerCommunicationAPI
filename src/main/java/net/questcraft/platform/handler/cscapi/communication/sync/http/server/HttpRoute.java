package net.questcraft.platform.handler.cscapi.communication.sync.http.server;

import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;
import spark.Response;

@FunctionalInterface
public interface HttpRoute {
    HttpPacket handle(HttpPacket packet, Response response);
}
