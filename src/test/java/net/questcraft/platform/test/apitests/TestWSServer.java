package net.questcraft.platform.test.apitests;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.ChannelType;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.DefaultChannelTypes;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import spark.Spark;

public class TestWSServer {
    public static void main(String[] args) throws CSCException, Exception {
        Spark.port(8000);
        SocketPipeline wsServer = (SocketPipeline) createWSServer(DefaultChannelTypes.SERVER_WS);
        wsServer.registerPacket(KryoTestClass.class);

//        Spark.webSocket("/somethingGreat", new TestSPHandler());
//        Spark.init();
    }

    private static ChannelPipeline createWSServer(ChannelType serverWss) throws CSCException, Exception {
        ChannelHandler handler = CSCAPI.getAPI().getChannelHandler(serverWss);
        SocketPipeline.Builder builder = new SocketPipeline.Builder("/somethingGreat", TestChannelPipeline.class);
        builder.autoReconnect(true);

        return handler.registerPipeline(builder);
    }
}
