package net.questcraft.platform.test.apitests;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.ChannelType;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import spark.Spark;

public class TestMain {
    public static void main(String[] args) throws CSCException, Exception {
        Spark.port(8000);
        SocketPipeline wsServer = (SocketPipeline) createWSServer(ChannelType.SERVER_WS);

//        Spark.webSocket("/somethingGreat", new TestSPHandler());
//        Spark.init();
    }

    private static ChannelPipeline createWSServer(ChannelType serverWss) throws CSCException, Exception {
        ChannelHandler handler = CSCAPI.getAPI().getChannelHandler(serverWss);
        ChannelPipeline pipeline = new TestChannelPipeline("/somethingGreat");

        return handler.registerPipeline(pipeline);
    }
}
