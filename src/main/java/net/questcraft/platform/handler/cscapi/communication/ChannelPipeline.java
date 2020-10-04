package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.error.CSCException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public interface ChannelPipeline {
    /**
     * Handles all incoming messaging of wsPacket
     *
     * @param packet
     */
    void onMessage(Packet packet);

    void sendMessage(Packet packet) throws IOException, CSCException;
}
