package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.serializer.*;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class SocketPipeline {
    private final String path;

    public SocketPipeline(String path) {
        this.path = path;
    }

    public abstract void onMessage(Session session, WBPacket packet);

    public abstract void onClose(Session user, int statusCode, String reason);

    public abstract void onConnect(Session user);

    public void sendBytes(Session session, WBPacket packet) throws IOException {
        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());
        byte[] byteArray = new byte[0];
        try {
            byteArray = serializer.serialize(packet);
        } catch (net.questcraft.platform.handler.cscapi.error.CSCException e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        session.getRemote().sendBytes(byteBuffer);
    }

    public String getPath() {
        return this.path;
    }

}
