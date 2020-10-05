package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.channels.Channel;

public interface ChannelPipeline {
    /**
     * Handles all incoming messaging of wsPacket
     *
     * @param packet
     */
    void onMessage(Packet packet);

    void sendMessage(Packet packet) throws IOException, CSCException;

    /**
     *
     * Once the ChannelPipeline is fully connected all queued
     * packets will be processed and sent to the server/client
     *
     * @param packet The packet that will be sent onConnect
     */
    void queueMessage(Packet packet);

    /**
     *
     * Once the ChannelPipeline is fully connected all queued
     * packets will be processed and sent to the server/client
     *
     * The packet array param will be directly added to the Buffer
     * in the order specified
     *
     * @param packets The packet[] that will be sent onConnect
     */

    void queueMessages(Packet[] packets);

    /**
     * Extends ChannelHandler#registerPacket Use to register any Packet
     *
     * @param packet The packet that will be registered
     */
    void registerPacket(Class<? extends Packet> packet);

    abstract class Builder {
        public abstract ChannelPipeline build(ChannelHandler handler) throws CSCInstantiationException;
    }
}
