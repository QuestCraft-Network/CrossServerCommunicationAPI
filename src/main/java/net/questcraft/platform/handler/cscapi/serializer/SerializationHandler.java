package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;

import java.io.IOException;

/**
 *
 * The Handler interface for all sub Serialization Handler types
 *
 * @author Chestly
 * @version 1.0-SNAPSHOT
 */

public interface SerializationHandler extends Serializer {
    <T> T serialize(Packet packet) throws IOException, CSCException;
}
