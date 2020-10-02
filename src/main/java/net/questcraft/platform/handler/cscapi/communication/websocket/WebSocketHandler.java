package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteDeserializationHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;

public class WebSocketHandler extends ChannelHandler {

    public WebSocketHandler() {
    }

    @Override
    public void onMessage(ChannelPipeline pipeline, Session session, Object packet) throws IOException, CSCException {
        if (!(packet instanceof byte[])) throw new IllegalArgumentException("Object Packet must be instance of byte[]");
        byte[] bytes = (byte[]) packet;

        DeserializationHandler serializationHandler = new ByteDeserializationHandler();
        WBPacket wbPacket = serializationHandler.deserialize(bytes, this.registeredClasses);

        pipeline.onMessage(wbPacket);
    }

    @Override
    public void registerPipeline(ChannelPipeline chPipeline) throws Exception {
        if (!(chPipeline instanceof SocketPipeline)) throw new IllegalArgumentException("ChannelPipeline type must be of SocketPipeline to be usable with the WebSocket API");
        SocketPipeline pipeline = (SocketPipeline) chPipeline;

        this.pipelines.add(pipeline);

        WebSocketClient client = new WebSocketClient();

        client.start();
        URI destination = URI.create(pipeline.getPath());
        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new SocketPipelineHandler(this, pipeline), destination, request);
    }

    public void onConnect(SocketPipeline pipeline, Session session) {
        pipeline.onConnect(session);
    }

    public void onClose(SocketPipeline pipeline, int statusCode, String reason) {
        pipeline.onClose(statusCode, reason);
    }

    public void onError(SocketPipeline pipeline, Throwable t) {
        pipeline.onError(t);
    }
}
