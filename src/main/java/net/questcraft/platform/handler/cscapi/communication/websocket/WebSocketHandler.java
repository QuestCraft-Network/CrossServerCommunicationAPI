package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.WebSocketException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteDeserializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public abstract class WebSocketHandler extends ChannelHandler {
    protected int RECONNECT_DELAY = 10;

    @Override
    public void onMessage(ChannelPipeline pipeline, Object packet) throws IOException, CSCException {
        if (!(packet instanceof byte[])) throw new IllegalArgumentException("Object Packet must be instance of byte[]");
        byte[] bytes = (byte[]) packet;

        DeserializationHandler serializationHandler = new ByteDeserializationHandler();
        serializationHandler.registerSerializer(this.serializers);
        Packet wsPacket = serializationHandler.deserialize(bytes, this.registeredClasses);

        pipeline.onMessage(wsPacket);
    }

    public void onConnect(SocketPipeline pipeline, Session session) { pipeline.onConnect(session); }

    public void onClose(SocketPipeline pipeline, int statusCode, String reason) { pipeline.onClose(statusCode, reason); }

    public void onError(SocketPipeline pipeline, Throwable t) {
        pipeline.onError(new WebSocketException(t));
    }

    protected abstract void reconnectPipeline(SocketPipeline pipeline) throws Exception;

    public void RECONNECT_DELAY(int delay) {
        this.RECONNECT_DELAY = delay;
    }
    public int RECONNECT_DELAY() {
        return this.RECONNECT_DELAY;
    }


}
