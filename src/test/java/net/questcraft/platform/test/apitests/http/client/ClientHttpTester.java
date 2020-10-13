package net.questcraft.platform.test.apitests.http.client;

import net.questcraft.platform.handler.cscapi.CSCAPI;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.SyncChannelType;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;

public class ClientHttpTester {
    public static void main(String[] args) throws CSCException, Exception {
        TestHttpClientPipeline pipeline = createPipe();
        pipeline.registerPacket(HttpPacketTester.class);
        HttpPacket httpPacket = pipeline.sendMessage("http://localhost:4567/doTHIS", new HttpPacketTester("HI", 100));
        System.out.println(httpPacket.toString());
    }

    public static TestHttpClientPipeline createPipe() throws CSCException, Exception {
        HttpChannelHandler channelHandler = CSCAPI.getAPI().getChannelHandler(SyncChannelType.CLIENT_HTTP);
        TestHttpClientPipeline testHttpServerPipeline = channelHandler.registerPipeline(new HttpPipeline.Builder<>(TestHttpClientPipeline.class));
        return testHttpServerPipeline;
    }
}
