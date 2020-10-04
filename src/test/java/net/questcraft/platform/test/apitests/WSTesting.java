package net.questcraft.platform.test.apitests;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.ChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;
import spark.Spark;

import java.net.URI;

public class WSTesting {
    @Test
    public void testClient() throws CSCException, Exception {
//        Spark.port(8001);
        ChannelPipeline client = createClient();
        Thread.sleep(1000*5000);
    }

    @Test
    public void testBareClient() throws Exception {
//        Spark.port(8001);

        WebSocketClient client = new WebSocketClient();

        client.start();

        URI destination = new URI("ws://localhost:8000/somethingGreat");

        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new TestSPHandler(), destination, request);

        Thread.sleep(1000*30);

//        Spark.awaitInitialization();
    }

    private ChannelPipeline createClient() throws CSCException, Exception {
        ChannelHandler channelHandler = CSCAPI.getAPI().getChannelHandler(ChannelType.CLIENT_WS);
        ChannelPipeline pipeline = new TestChannelPipeline("ws://localhost:8000/somethingGreat");

        return channelHandler.registerPipeline(pipeline);
    }

    @Test
    public void testServer() throws CSCException, Exception {


        Spark.awaitInitialization();




//        wsClient.sendMessage(new KyroTestClass(1, 5, "COOOL"));

    }


}
