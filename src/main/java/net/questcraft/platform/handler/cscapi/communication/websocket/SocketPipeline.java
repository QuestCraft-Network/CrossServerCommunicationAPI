package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.*;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class SocketPipeline implements ChannelPipeline {
    private final String path;
    private final Session session;

    public SocketPipeline(String path, Session session) {
        this.path = path;
        this.session = session;
    }

    /**
     * Must be implemented by all Children, Handler for recieving all incoming Data packets
     *
     * @param packet The incoming WebSocket Packet
     */
    public abstract void onMessage(WBPacket packet);

    /**
     * Optionally implemented, Called when the websocket connection is lossed
     *
     * @param statusCode Status code for the websocket connection loss
     * @param reason Reason for the websocket connection loss
     */
    public void onClose(int statusCode, String reason) { }

    /**
     * Optionally implemented, Called when the websocket is successfully connected to the server
     *
     * @param session The WebSocket Session after connected
     */
    public void onConnect(Session session) { }

    /**
     * Optionally implemented, Called when there is a WebSocket error internally
     *
     * @param t Throwable of the corresponding error
     */
    public void onError(Throwable t) { }


    public String getPath() {
        return this.path;
    }

    public void sendMessage(Session session, WBPacket packet) throws IOException, CSCException {
        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());
        byte[] byteArray = serializer.serialize(packet);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        session.getRemote().sendBytes(byteBuffer);
    }


}
