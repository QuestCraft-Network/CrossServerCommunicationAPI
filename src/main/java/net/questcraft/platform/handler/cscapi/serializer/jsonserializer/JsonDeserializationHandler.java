package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.json.JsonPacketDeserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class JsonDeserializationHandler extends JsonSerialization implements DeserializationHandler {
    @Override
    public Packet deserialize(Object packet, Set<Class<? extends Packet>> applicableClasses) throws IOException, CSCException {
        if (!(packet instanceof String)) throw new IllegalArgumentException("Packet type must be of type String");
        String jsonPacket = (String) packet;

        String classID = this.getClassID(jsonPacket);

        return this.objectMapper.readValue(this.removeIDDescriptor(jsonPacket), this.getFromApplicable(classID, applicableClasses));
    }

    private boolean isDeserializable(String str) {
        return str.startsWith(NAME_IDENTIFIER);
    }

    private String getClassID(String json) {
        if (!this.isDeserializable(json)) throw new IllegalArgumentException("Given string is not serializable");

        int start = json.indexOf(NAME_IDENTIFIER) + NAME_IDENTIFIER.length() + NAME_WRAPPER[0].length();
        int end = json.indexOf(NAME_WRAPPER[1]);

        return json.substring(start, end);
    }

    private Class<? extends Packet> getFromApplicable(String clsID, Set<Class<? extends Packet>> applicableClasses) throws InvalidClassIDDescriptor {
        for (Class<? extends Packet> cls : applicableClasses) {
            String currentClsID = this.getSerializationKey(cls);

            if (currentClsID.equals(clsID)) return cls;
        }
        throw new IllegalArgumentException("Provided ClassID descriptor matches no applicable class");
    }

    private String removeIDDescriptor(String str) {
        String id = this.getClassID(str);
        String descriptor = NAME_IDENTIFIER + NAME_WRAPPER[0] + id + NAME_WRAPPER[1];
        return str.substring(descriptor.length());
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
        if (!JsonPacketDeserializer.class.isAssignableFrom(serializer.getClass())) throw new IllegalArgumentException("Type must be JsonPacketDeserializer");
        JsonPacketDeserializer<T> deserializer = (JsonPacketDeserializer<T>) serializer;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(cls, deserializer);
        this.objectMapper.registerModule(module);
    }
}
