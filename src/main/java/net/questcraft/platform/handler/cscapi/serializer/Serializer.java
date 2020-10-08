package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.serializer.serializers.BytePacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.util.Map;

public interface Serializer {
    void registerSerializer(Class<?> type, PacketSerializer serializer) throws IllegalArgumentException;
    void registerSerializer(Map<Class<?>, PacketSerializer> serializers) throws IllegalArgumentException;

}
