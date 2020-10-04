package net.questcraft.platform.handler.cscapi.communication.websocket.client;

import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipelineHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.WebSocketHandler;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import spark.Spark;

import java.net.URI;


public class WebSocketClientHandler extends WebSocketHandler {
    public WebSocketClientHandler() {
    }

    @Override
    public ChannelPipeline  registerPipeline(ChannelPipeline chPipeline) throws Exception {
        if (!(chPipeline instanceof SocketPipeline)) throw new IllegalArgumentException("ChannelPipeline type must be of SocketPipeline to be usable with the WebSocket API");
        SocketPipeline pipeline = (SocketPipeline) chPipeline;

        this.pipelines.add(pipeline);

//        Spark.init(); No need

        WebSocketClient client = new WebSocketClient();

        client.start();

        URI destination = new URI(pipeline.getPath());

        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new SocketPipelineHandler(this, pipeline), destination, request);

        return chPipeline;
    }


    @Override
    protected void reconnectPipeline(SocketPipeline pipeline) throws Exception {
        WebSocketClient client = new WebSocketClient();

        client.start();
        URI destination = new URI(pipeline.getPath());
        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new SocketPipelineHandler(this, pipeline), destination, request);
    }
}