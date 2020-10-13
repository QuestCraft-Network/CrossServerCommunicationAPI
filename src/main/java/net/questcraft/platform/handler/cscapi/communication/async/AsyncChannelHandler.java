package net.questcraft.platform.handler.cscapi.communication.async;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.sync.SyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AsyncChannelHandler extends CommunicationHandler {
    protected final List<AsyncChannelPipeline> pipelines;

    public AsyncChannelHandler(SerializationHandler serializationHandler, DeserializationHandler deserializationHandler) {
        super(serializationHandler, deserializationHandler);
        this.pipelines = new ArrayList<>();
    }

    /**
     * Constructs a new ChannelHandler and initializes final member variables.
     * MUST Have an empty constructor in any extensible child of ChannelHandler
     */
    public <T extends AsyncChannelPipeline> T registerPipeline(AsyncChannelPipeline.Builder<T> builder) throws CSCInstantiationException, Exception {
        T pipeline = builder.build(this);
        this.pipelines.add(pipeline);
        return pipeline;
    }

    public void registerPacket(Class<? extends Packet> cls) {
        this.registeredClasses.add(cls);
    }

    protected abstract void onMessage(AsyncChannelPipeline pipeline, Object packet) throws IOException, CSCException, IllegalAccessException;

    public boolean isPacketRegistered(Class<?> cls) {
        return this.registeredClasses.contains(cls);
    }



    public SerializationHandler getSerializationHandler() {
        return serializationHandler;
    }

    public DeserializationHandler getDeserializationHandler() {
        return deserializationHandler;
    }
}
