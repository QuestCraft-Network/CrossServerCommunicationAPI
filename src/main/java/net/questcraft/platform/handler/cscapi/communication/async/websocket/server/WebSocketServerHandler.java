package net.questcraft.platform.handler.cscapi.communication.async.websocket.server;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipelineHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;
import net.questcraft.platform.handler.cscapi.communication.spark.cache.WebSocketCache;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import spark.Spark;

public class WebSocketServerHandler extends SocketChannelHandler {
    public WebSocketServerHandler() {
    }

    /**
     * Registers a ChannelPipeline with the WebSocketHandler
     *
     * @param builder A ChannelPipelines Builder to initiate a new ChannelPipeline
     * @return ChannelPipeline, Will return the initiated ChannelPipeline
     * @throws CSCInstantiationException If fails to instantiate the given class
     * @throws Exception                 Throws in the case of a invalid ChannelPipeline#Builder
     */
    @Override
    public <T extends AsyncChannelPipeline> T registerPipeline(ChannelPipeline.Builder<T> builder) throws CSCInstantiationException, Exception {
        if (builder.isProductInstanceOf(SocketPipeline.class))
            throw new IllegalArgumentException("ChannelPipeline type must be of SocketPipeline to be usable with the WebSocket API");
        T chPipeline = super.registerPipeline(builder);

        SocketPipeline pipeline = (SocketPipeline) chPipeline;

        SparkCacheHandler.SparkCache cache = new WebSocketCache(pipeline, this);
        SparkCacheHandler.writeCache(cache);

        return chPipeline;
    }

    /**
     *  A protected utility for reconnecting broken pipelines
     *
     * @param pipeline The Pipeline to reconnect
     * @throws Exception In case of a invalid URI or failure to reconnect the client
     */
    @Override
    protected void reconnectPipeline(SocketPipeline pipeline) {
        Spark.webSocket(pipeline.getPath(), new SocketPipelineHandler(this, pipeline));
    }
}
