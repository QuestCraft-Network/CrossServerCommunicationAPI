package net.questcraft.platform.test.apitests;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class TestSPHandler {

    @OnWebSocketMessage
    public void onMessage(Session session, String string) throws IOException {
        System.out.println(string);
        session.getRemote().sendString("HI");
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected");
        try {
            session.getRemote().sendString("OLLA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Closed");

    }

}
