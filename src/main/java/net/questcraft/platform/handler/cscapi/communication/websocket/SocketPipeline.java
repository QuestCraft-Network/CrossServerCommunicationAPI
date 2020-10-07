package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.messagebuffer.MessageBuffer;
import net.questcraft.platform.handler.cscapi.communication.messagebuffer.PacketMessageBuffer;
import net.questcraft.platform.handler.cscapi.error.*;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

public abstract class SocketPipeline implements ChannelPipeline {
    private final String path;
    private Session session;

    private final MessageBuffer queueBuffer;
    private final ChannelHandler handler;

    private boolean isConnected = false;
    private boolean autoReconnect;


    public SocketPipeline(Builder builder) {
        this.path = builder.path;
        this.autoReconnect = builder.autoReconnect;
        this.handler = builder.handler;

        this.queueBuffer = new PacketMessageBuffer();
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
     * @param reason     Reason for the websocket connection loss
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

        try {
            this.sendQueuedMessages();
        } catch (IOException | CSCException e) {
            throw new RuntimeException("Failed to send messages Stored in Queue on WebSocket Connect. Error '" + e.getMessage() + "'");
        }
    }

    /**
     * Optionally implemented, Called when there is a WebSocket error internally
     *
     * @param e Throwable of the corresponding error
     */
    public void onError(WebSocketException e) {
    }

    @Override
    public void registerPacket(Class<? extends Packet> packet) {
        this.handler.registerPacket(packet);
    }


    public String getPath() {
        return this.path;
    }

    public Session getSession() {
        return session;
    }

    public boolean willReconnect() {
        return this.autoReconnect;
    }

    public void setReconnect(boolean reconnect) {
        this.autoReconnect = reconnect;
    }

    public boolean isConnected() {
        return this.isConnected;
    }


    @Override
    public void sendMessage(Packet packet) throws IOException, CSCException {
        if (!this.isConnected) this.queueBuffer.write(packet);
        if (!this.handler.isPacketRegistered(packet.getClass()))
            throw new UnregisteredPacketException("Packet type of " + packet.getClass().toString() + " is currently unregistered, please Register with SocketPipeline#registerPacket or ChannelPipeline#registerPacket");

        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());
        byte[] byteArray = serializer.serialize(packet);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        this.session.getRemote().sendBytes(byteBuffer);
    }

    public void closeConnection() throws UnconnectedWebSocketException {
        if (!this.isConnected)
            throw new UnconnectedWebSocketException("Current Websocket is not connected, There must be a open connection to be able to close it");
        this.autoReconnect = false;
        this.session.close();
    }

    @Override
    public void queueMessage(Packet packet) {
        this.queueBuffer.write(packet);
    }

    @Override
    public void queueMessages(Packet[] packets) {
        this.queueBuffer.write(packets);
    }

    /**
     *  A private utility for sending all messages in the Queue buffer
     *
     * @throws IOException If send fails
     * @throws CSCException If Packet type is unregistered
     */
    private void sendQueuedMessages() throws IOException, CSCException {
        if (this.queueBuffer.isEmpty()) return;
        Packet[] packets = this.queueBuffer.toPacketArray();
        for (Packet packet : packets) {
            this.sendMessage(packet);
        }
        this.queueBuffer.empty();
    }

    /**
     * The Builder for all SocketPipelines
     *
     * @author Chestly
     */
    public static class Builder extends ChannelPipeline.Builder {
        private final String path;
        private boolean autoReconnect;
        private ChannelHandler handler;

        public Builder(String path, Class<? extends ChannelPipeline> pipeCls) {
            super(pipeCls);
            this.path = path;
        }

        @Override
        public SocketPipeline build(ChannelHandler handler) throws CSCInstantiationException {
            try {
                if (!SocketPipeline.class.isAssignableFrom(this.pipeCls))
                    throw new IllegalArgumentException("Class is not of type SocketPipeline, To be registered as a Pipeline this must be the case");
                Class<? extends SocketPipeline> socketCls = (Class<? extends SocketPipeline>) this.pipeCls;

                this.handler = handler;

                Constructor<? extends SocketPipeline> constructor = socketCls.getConstructor(Builder.class);
                SocketPipeline socketPipeline = constructor.newInstance(this);
                return socketPipeline;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new CSCInstantiationException("Failed to instantiate SocketPipeline of type : " + this.pipeCls.toString());
            }
        }

        public Builder autoReconnect(boolean reconnect) {
            this.autoReconnect = reconnect;
            return this;
        }
    }
}
