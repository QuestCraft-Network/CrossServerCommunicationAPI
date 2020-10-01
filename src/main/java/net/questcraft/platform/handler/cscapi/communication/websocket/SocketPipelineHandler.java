package net.questcraft.platform.handler.cscapi.communication.websocket;


import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Spark;

import java.io.IOException;

@WebSocket
public class SocketPipelineHandler {
    private final CommunicationHandler handler;

    public SocketPipelineHandler(CommunicationHandler handler) {
        this.handler = handler;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, byte[] b) {
        try {
            this.handler.onMessage(session, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }

    @OnWebSocketConnect
    public void onConnect(Session session) {

    }

    @OnWebSocketError
    public void onError(Throwable t) {

    }
}
