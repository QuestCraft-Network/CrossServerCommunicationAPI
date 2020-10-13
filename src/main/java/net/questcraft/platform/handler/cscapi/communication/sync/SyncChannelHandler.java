package net.questcraft.platform.handler.cscapi.communication.sync;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;

import java.util.HashSet;
import java.util.Set;

public abstract class SyncChannelHandler extends CommunicationHandler {
    protected final Set<SyncChannelPipeline> pipelines;

    public SyncChannelHandler(SerializationHandler serializationHandler, DeserializationHandler deserializationHandler) {
        super(serializationHandler, deserializationHandler);
        this.pipelines = new HashSet<>();
    }

    public SerializationHandler getSerializationHandler() {
        return serializationHandler;
    }

    public DeserializationHandler getDeserializationHandler() {
        return deserializationHandler;
    }

    public void registerPacket(Class<? extends Packet> cls) {
        this.registeredClasses.add(cls);
    }

    public <T extends SyncChannelPipeline> T registerPipeline(SyncChannelPipeline.Builder<T> builder) throws CSCInstantiationException, Exception {
        T pipeline = builder.build(this);
        this.pipelines.add(pipeline);
        return pipeline;
    }

    public Set<Class<? extends Packet>> getRegisteredClasses() {
        return registeredClasses;
    }
}
