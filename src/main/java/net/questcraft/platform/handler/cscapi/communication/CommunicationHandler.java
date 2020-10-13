package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.util.HashSet;
import java.util.Set;

public abstract class CommunicationHandler {
    protected final SerializationHandler serializationHandler;
    protected final DeserializationHandler deserializationHandler;

    protected final Set<Class<? extends Packet>> registeredClasses;

    public CommunicationHandler(SerializationHandler serializationHandler, DeserializationHandler deserializationHandler) {
        this.serializationHandler = serializationHandler;
        this.deserializationHandler = deserializationHandler;

        this.registeredClasses = new HashSet<>();
    }

    public <T> void registerSerializer(Class<T> cls, PacketSerializer<T> serializer) {
        this.serializationHandler.registerSerializer(cls, serializer);
        this.deserializationHandler.registerSerializer(cls, serializer);
    }
}
