package net.questcraft.platform.handler.cscapi.communication.sync.http;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.sync.SyncChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.sync.SyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.jsonserializer.JsonDeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.jsonserializer.JsonSerializationHandler;

public class HttpChannelHandler extends SyncChannelHandler {

    /**
     * Provides a DeserializationHandler and a SerializationHandler on construction
     */
    public HttpChannelHandler() {
        super(new JsonSerializationHandler(), new JsonDeserializationHandler());
    }

    /**
     * Overrides its superior SyncChannelHandler#registerPipeline(ChannelPipeline.Builder<T>) To initiate all HttpPipelines
     *
     * @param builder The Builder to innit the HttpChannelHandler
     * @param <T> The Class<? extends SyncChannelPipeline> to build
     * @return The instantiated ChannelPipeline
     * @throws CSCInstantiationException If the Builder fails to build
     * @throws Exception If the Builder fails to build
     */
    @Override
    public <T extends SyncChannelPipeline> T registerPipeline(ChannelPipeline.Builder<T> builder) throws CSCInstantiationException, Exception {
        T t = super.registerPipeline(builder);
        HttpPipeline pipeline = (HttpPipeline) t;
        pipeline.innit();

        return t;
    }
}
