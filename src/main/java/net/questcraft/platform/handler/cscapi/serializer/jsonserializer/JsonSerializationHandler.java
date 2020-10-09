package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import net.questcraft.platform.handler.cscapi.communication.async.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.io.IOException;
import java.util.Map;

public class JsonSerializationHandler extends JsonSerialization implements SerializationHandler {
    @Override
    public Object serialize(Packet packet) throws IOException, CSCException {
        return null;
    }

    @Override
    public void registerSerializer(Class<?> type, PacketSerializer serializer) throws IllegalArgumentException {

    }

    @Override
    public void registerSerializer(Map<Class<?>, PacketSerializer> serializers) throws IllegalArgumentException {

    }
}
