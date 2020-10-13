package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

public interface ChannelPipeline {

    void registerPacket(Class<? extends Packet> packet);

    <T> void registerSerializer(Class<T> cls, PacketSerializer<T> serializer);

    abstract class Builder<T extends ChannelPipeline> {
        protected final Class<T> pipeCls;

        public Builder(Class<T> pipeCls) {
            this.pipeCls = pipeCls;
        }

        public abstract <T extends ChannelPipeline> T build(CommunicationHandler handler) throws CSCInstantiationException;

        public boolean isProductInstanceOf(Class<?> cls) {
            return (this.pipeCls.isAssignableFrom(cls));
        }
    }
}
