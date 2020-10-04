package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.error.CSCException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ChannelHandler {
    protected final List<ChannelPipeline> pipelines;
    protected final Set<Class<?>> registeredClasses;

    /**
     * Constructs a new ChannelHandler and initializes final member variables.
     * MUST Have an empty constructor in any extensible child of ChannelHandler
     */
    public ChannelHandler() {
        this.pipelines = new ArrayList<>();
        this.registeredClasses = new HashSet<>();
    }

    public ChannelPipeline registerPipeline(ChannelPipeline pipeline) throws Exception {
        this.pipelines.add(pipeline);
        return pipeline;
    }

    public void registerPacket(Class<?> cls) {
        this.registeredClasses.add(cls);
    }

    protected abstract void onMessage(ChannelPipeline pipeline, Object packet) throws IOException, CSCException;


}
