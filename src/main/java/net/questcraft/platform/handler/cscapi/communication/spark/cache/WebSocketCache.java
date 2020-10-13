package net.questcraft.platform.handler.cscapi.communication.spark.cache;

import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipelineHandler;
import spark.Spark;

import java.util.Objects;

public class WebSocketCache extends SparkCacheHandler.SparkCache {
    private final SocketPipeline pipeline;
    private final SocketChannelHandler handler;

    public WebSocketCache(SocketPipeline pipeline, SocketChannelHandler handler) {
        super(2);
        this.pipeline = pipeline;
        this.handler = handler;
    }

    @Override
    public void register() {
        Spark.webSocket(pipeline.getPath(), new SocketPipelineHandler(this.handler, this.pipeline));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebSocketCache that = (WebSocketCache) o;
        return Objects.equals(pipeline, that.pipeline) &&
                Objects.equals(handler, that.handler);
    }
}
