package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.json.JsonPacketDeserializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.json.JsonPacketSerialization;
import net.questcraft.platform.handler.cscapi.serializer.serializers.json.JsonPacketSerializer;

import java.io.IOException;
import java.util.Map;

public class JsonSerializationHandler extends JsonSerialization implements SerializationHandler {
    @Override
    public String serialize(Packet packet) throws IOException, CSCException {
        String json = NAME_IDENTIFIER + NAME_WRAPPER[0] + this.getSerializationKey(packet.getClass()) + NAME_WRAPPER[1] + this.objectMapper.writeValueAsString(packet);
        return json;
    }

    @Override
    public <T> void registerSerializer(Class<T> type, PacketSerializer<T> serializer) throws IllegalArgumentException {
        registerModule(type, serializer);
    }

    @Override
    public <T> void registerSerializer(Map<Class<T>, PacketSerializer<T>> serializers) throws IllegalArgumentException {
        for (Class<T> cls : serializers.keySet()) {
            PacketSerializer<T> serializer = serializers.get(cls);

            registerModule(cls, serializer);
        }
    }

    private <T> void registerModule(Class<T> cls, PacketSerializer<T> serializer) {
        if (!JsonPacketSerializer.class.isAssignableFrom(serializer.getClass())) throw new IllegalArgumentException("Type must be JsonPacketDeserializer");
        JsonPacketSerializer<T> jsonPacketSerializer = (JsonPacketSerializer<T>) serializer;

        SimpleModule module = new SimpleModule();
        module.addSerializer(cls, jsonPacketSerializer);
        this.objectMapper.registerModule(module);
    }
}
