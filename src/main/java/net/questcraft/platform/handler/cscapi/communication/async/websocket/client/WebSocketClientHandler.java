package net.questcraft.platform.handler.cscapi.communication.async.websocket.client;

import net.questcraft.platform.handler.cscapi.communication.async.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipelineHandler;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.WebSocketHandler;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;


public class  WebSocketClientHandler extends WebSocketHandler {
    public WebSocketClientHandler() {
    }

    /**
     * Registers a ChannelPipeline with the WebSocketHandler
     *
     * @param builder A ChannelPipelines Builder to initiate a new ChannelPipeline
     * @return ChannelPipeline, Will return the initiated ChannelPipeline
     * @throws CSCInstantiationException If fails to instantiate the given class
     */
    @Override
    public ChannelPipeline registerPipeline(ChannelPipeline.Builder builder) throws CSCInstantiationException, Exception {
        if (builder.isProductInstanceOf(SocketPipeline.class))
            throw new IllegalArgumentException("ChannelPipeline type must be of SocketPipeline to be usable with the WebSocket API");

        ChannelPipeline chPipeline = super.registerPipeline(builder);
        SocketPipeline pipeline = (SocketPipeline) chPipeline;

        WebSocketClient client = new WebSocketClient();
        client.start();

        URI destination = new URI(pipeline.getPath());
        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new SocketPipelineHandler(this, pipeline), destination, request);

        return chPipeline;
    }


    /**
     *  A protected utility for reconnecting broken pipelines
     *
     * @param pipeline The Pipeline to reconnect
     * @throws Exception In case of a invalid URI or failure to reconnect the client
     */
    @Override
    protected void reconnectPipeline(SocketPipeline pipeline) throws Exception {
        if (pipeline.isConnected()) return;
        Thread.sleep(RECONNECT_DELAY);
        WebSocketClient client = new WebSocketClient();
        client.start();

        URI destination = new URI(pipeline.getPath());
        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new SocketPipelineHandler(this, pipeline), destination, request);
    }
}
