package net.questcraft.platform.test.apitests.ws;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.AsyncChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import spark.Spark;

public class TestWSServer {
    public static void main(String[] args) throws CSCException, Exception {
        Spark.port(8000);
        SocketPipeline wsServer = (SocketPipeline) createWSServer();
        wsServer.registerPacket(KryoTestClass.class);


//        Spark.webSocket("/somethingGreat", new TestSPHandler());
//        Spark.init();
    }

    public static TestChannelPipeline createWSServer() throws CSCException, Exception {
        AsyncChannelHandler handler = CSCAPI.getAPI().getChannelHandler(AsyncChannelType.SERVER_WS);
        SocketPipeline.Builder<TestChannelPipeline> builder = new SocketPipeline.Builder<>("/somethingGreat", TestChannelPipeline.class);
        builder.autoReconnect(true);


        return handler.registerPipeline(builder);
    }
}
