package net.questcraft.platform.handler.cscapi.communication.sync;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

public interface SyncChannelPipeline extends ChannelPipeline {
    @Override
    void registerPacket(Class<? extends Packet> packet);

    @Override
    <T> void registerSerializer(Class<T> cls, PacketSerializer<T> serializer);
}
