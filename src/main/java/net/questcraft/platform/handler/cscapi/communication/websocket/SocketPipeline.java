package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.UnconnectedWebSocketException;
import net.questcraft.platform.handler.cscapi.error.WebSocketException;
import net.questcraft.platform.handler.cscapi.serializer.*;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class SocketPipeline implements ChannelPipeline {
    private final String path;
    private Session session;

    private boolean isConnected = false;

    private boolean autoReconnect;

    public SocketPipeline(String path, boolean autoReconnect) {
        this.path = path;
        this.autoReconnect = autoReconnect;
    }

    /**
     * Must be implemented by all Children, Handler for recieving all incoming Data packets
     *
     * @param packet The incoming WebSocket Packet
     */
    public abstract void onMessage(Packet packet);

    /**
     * Optionally implemented, Called when the websocket connection is lossed
     *
     * @param statusCode Status code for the websocket connection loss
     * @param reason Reason for the websocket connection loss
     */
    public void onClose(int statusCode, String reason) {
       this.isConnected = false;
    }

    /**
     * Optionally implemented, Called when the websocket is successfully connected to the server
     *
     * @param session The WebSocket Session after connected
     */
    public void onConnect(Session session) {
        this.isConnected = true;
        this.session = session;
    }

    /**
     * Optionally implemented, Called when there is a WebSocket error internally
     *
     * @param e Throwable of the corresponding error
     */
    public void onError(WebSocketException e) { }


    public String getPath() {
        return this.path;
    }

    public Session getSession() { return session; }

    public boolean willReconnect() { return this.autoReconnect; }

    public void setReconnect(boolean reconnect) { this.autoReconnect = reconnect; }


    @Override
    public void sendMessage(Packet packet) throws IOException, CSCException {
        if (!this.isConnected) throw new UnconnectedWebSocketException("Current Websocket is not connected, to send a message please make sure you have correctly initiated the connection");
        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());
        byte[] byteArray = serializer.serialize(packet);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        this.session.getRemote().sendBytes(byteBuffer);
    }

    public void closeConnection() throws UnconnectedWebSocketException {
        if (!this.isConnected) throw new UnconnectedWebSocketException("Current Websocket is not connected, There must be a open connection to be able to close it");
        this.autoReconnect = false;
        this.session.close();
    }

}
