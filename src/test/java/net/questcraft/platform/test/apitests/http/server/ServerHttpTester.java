package net.questcraft.platform.test.apitests.http.server;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.SyncChannelType;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;
import spark.Spark;

public class ServerHttpTester {
    public static void main(String[] args) throws CSCException, Exception {
        TestHttpServerPipeline channelHandler = createServer();
        channelHandler.registerPacket(HttpPacketTester.class);

        Spark.awaitInitialization();

        System.out.println(channelHandler);
    }

    public static TestHttpServerPipeline createServer() throws CSCException, Exception {
        HttpChannelHandler channelHandler = CSCAPI.getAPI().getChannelHandler(SyncChannelType.SERVER_HTTP);
        TestHttpServerPipeline syncChannelPipeline = channelHandler.registerPipeline(new HttpPipeline.Builder<>(TestHttpServerPipeline.class));
        return syncChannelPipeline;
    }
}
