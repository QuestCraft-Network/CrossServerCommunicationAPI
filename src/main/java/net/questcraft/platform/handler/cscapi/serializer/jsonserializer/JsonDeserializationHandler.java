package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import net.questcraft.platform.handler.cscapi.communication.async.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class JsonDeserializationHandler implements DeserializationHandler {
    @Override
    public Packet deserialize(Object packet, Set<Class<?>> applicableClasses) throws IOException, CSCException {
        return null;
    }

    @Override
    public void registerSerializer(Class<?> type, PacketSerializer serializer) throws IllegalArgumentException {

    }

    @Override
    public void registerSerializer(Map<Class<?>, PacketSerializer> serializers) throws IllegalArgumentException {

    }
}
