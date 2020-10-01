package net.questcraft.platform.handler.cscapi.serializer;

import net.questcraft.platform.handler.cscapi.serializer.serializers.BytePacketSerializer;

public interface Serializer {
    void registerSerializer(Class<?> type, BytePacketSerializer<?> serializer);
}
