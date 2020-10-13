package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.util.Map;

public interface Serializer {
    <T> void registerSerializer(Class<T> type, PacketSerializer<T> serializer) throws IllegalArgumentException;
    <T> void registerSerializer(Map<Class<T>, PacketSerializer<T>> serializers) throws IllegalArgumentException;

}
