package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.communication.websocket.WBPacket;
import net.questcraft.platform.handler.cscapi.error.CSCException;

import java.io.IOException;
import java.util.Set;

/**
 *
 * The Handler interface for all sub Deserialization Handler types
 *
 * @author Chestly
 * @version 1.0-SNAPSHOT
 */

public interface DeserializationHandler extends Serializer {
    WBPacket deserialize(Object packet, Set<Class<?>> applicableClasses) throws IOException, CSCException;
}