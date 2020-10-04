package net.questcraft.platform.handler.cscapi.communication.websocket;


import net.questcraft.platform.handler.cscapi.error.CSCException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket
public class SocketPipelineHandler {
    private final WebSocketHandler handler;
    private final SocketPipeline pipeline;

    public SocketPipelineHandler(WebSocketHandler handler, SocketPipeline pipeline) {
        this.handler = handler;
        this.pipeline = pipeline;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, byte[] bytes, int a, int b) {
        try {
            this.handler.onMessage(this.pipeline, bytes);
        } catch (IOException | CSCException e) {
            throw new RuntimeException("Error Handling Socket Message. Error '" + e.getMessage() + "'");
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.handler.onConnect(this.pipeline, session);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        try {
            if (this.pipeline.willReconnect()) this.handler.reconnectPipeline(this.pipeline);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to reconnect the Socket pipeline. Error '" + e.getMessage() + "'");
        }
        this.handler.onClose(this.pipeline, statusCode, reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        this.handler.onError(this.pipeline, t);
    }
}
