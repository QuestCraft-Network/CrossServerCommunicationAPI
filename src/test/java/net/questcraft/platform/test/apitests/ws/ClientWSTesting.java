package net.questcraft.platform.test.apitests.ws;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.client.WebSocketClientHandler;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.AsyncChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;

import java.net.URI;

public class ClientWSTesting {
    public static void main(String[] args) {
        try {
            AsyncChannelPipeline client = createClient();
            client.registerPacket(KryoTestClass.class);
            for (int i = 0; i < 100; i++) {
                client.queueMessage(new KryoTestClass(1, 10, "HEY MAN", new SubClassTest("HI", 5)));
            }

        } catch (CSCException | Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBareClient() throws Exception {
//        Spark.port(8001);

        WebSocketClient client = new WebSocketClient();

        client.start();

        URI destination = new URI("ws://localhost:8000/somethingGreat");

        ClientUpgradeRequest request = new ClientUpgradeRequest();

        client.connect(new TestSPHandler(), destination, request);

        Thread.sleep(1000 * 30);

//        Spark.awaitInitialization();
    }//ws://192.168.0.28:8000/somethingGreat

    public static TestChannelPipeline createClient() throws CSCException, Exception {
        WebSocketClientHandler channelHandler = CSCAPI.getAPI().getChannelHandler(AsyncChannelType.CLIENT_WS);
        SocketPipeline.Builder<TestChannelPipeline> builder = new SocketPipeline.Builder<>("ws://localhost:8000/somethingGreat", TestChannelPipeline.class);
        builder.autoReconnect(true);

        return channelHandler.registerPipeline(builder);
    }



}
