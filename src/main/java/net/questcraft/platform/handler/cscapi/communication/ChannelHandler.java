package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.io.IOException;
import java.util.*;

public abstract class ChannelHandler {
    protected final List<ChannelPipeline> pipelines;
    protected final Set<Class<?>> registeredClasses;
    protected final Map<Class<?>, PacketSerializer> serializers;

    /**
     * Constructs a new ChannelHandler and initializes final member variables.
     * MUST Have an empty constructor in any extensible child of ChannelHandler
     */
    public ChannelHandler() {
        this.pipelines = new ArrayList<>();
        this.registeredClasses = new HashSet<>();
        this.serializers = new HashMap<>();
    }

    public ChannelPipeline registerPipeline(ChannelPipeline.Builder builder) throws CSCInstantiationException, Exception {
        ChannelPipeline pipeline = builder.build(this);
        this.pipelines.add(pipeline);
        return pipeline;
    }

    public void registerPacket(Class<?> cls) {
        this.registeredClasses.add(cls);
    }

    protected abstract void onMessage(ChannelPipeline pipeline, Object packet) throws IOException, CSCException, IllegalAccessException;

    public boolean isPacketRegistered(Class<?> cls) {
        return this.registeredClasses.contains(cls);
    }

    public void registerSerializer(Class<?> cls, PacketSerializer serializer) {
        this.serializers.put(cls, serializer);
    }

    public Map<Class<?>, PacketSerializer> getSerializers() {
        return serializers;
    }
}
