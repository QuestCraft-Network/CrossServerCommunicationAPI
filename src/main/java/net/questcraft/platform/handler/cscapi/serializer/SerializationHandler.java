package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.communication.websocket.WBPacket;
import net.questcraft.platform.handler.cscapi.error.CSCException;

import java.io.IOException;

/**
 *
 * The Handler interface for all sub Serialization Handler types
 *
 * @author Chestly
 * @version 1.0-SNAPSHOT
 */

public interface SerializationHandler<T> extends Serializer {
    T serialize(WBPacket packet) throws IOException, CSCException;
}
