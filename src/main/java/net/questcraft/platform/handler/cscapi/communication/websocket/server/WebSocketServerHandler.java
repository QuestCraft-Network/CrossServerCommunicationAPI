package net.questcraft.platform.handler.cscapi.communication.websocket.server;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipelineHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.WebSocketHandler;
import spark.Spark;

public class WebSocketServerHandler extends WebSocketHandler {
    public WebSocketServerHandler() {
    }

    @Override
    public ChannelPipeline registerPipeline(ChannelPipeline chPipeline) {
        if (!(chPipeline instanceof SocketPipeline)) throw new IllegalArgumentException("ChannelPipeline type must be of SocketPipeline to be usable with the WebSocket API");
        SocketPipeline pipeline = (SocketPipeline) chPipeline;

        this.pipelines.add(pipeline);

        Spark.webSocket(pipeline.getPath(), new SocketPipelineHandler(this, pipeline));
        Spark.init();
        return chPipeline;
    }

    @Override
    protected void reconnectPipeline(SocketPipeline pipeline) {
        Spark.webSocket(pipeline.getPath(), new SocketPipelineHandler(this, pipeline));
    }
}
