package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.communication.websocket.WBPacket;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public interface ChannelPipeline {
    /**
     * Handles all incoming messaging of WBPacket
     *
     * @param packet
     */
    void onMessage(Packet packet);

    void sendMessage(Session session, Packet packet) throws IOException, CSCException;
}
