package net.questcraft.platform.handler.cscapi.communication.websocket;


import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
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
    public void onMessage(Session session, byte[] b) {
        try {
            this.handler.onMessage(session, b);
        } catch (IOException | CSCException e) {
            //TODO Create error handling for low level websocket work
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
