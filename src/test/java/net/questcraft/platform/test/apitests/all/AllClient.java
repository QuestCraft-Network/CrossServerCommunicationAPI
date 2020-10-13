package net.questcraft.platform.test.apitests.all;

import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;
import net.questcraft.platform.test.apitests.http.client.ClientHttpTester;
import net.questcraft.platform.test.apitests.http.client.TestHttpClientPipeline;
import net.questcraft.platform.test.apitests.ws.ClientWSTesting;
import net.questcraft.platform.test.apitests.ws.SubClassTest;
import net.questcraft.platform.test.apitests.ws.TestChannelPipeline;

public class AllClient {
    public static void main(String[] args) throws CSCException, Exception {
        TestHttpClientPipeline pipeline = ClientHttpTester.createPipe();

        TestChannelPipeline client = ClientWSTesting.createClient();

        pipeline.registerPacket(HttpPacketTester.class);
        client.registerPacket(KryoTestClass.class);

        pipeline.sendMessage("http://localhost:8000/doTHIS", new HttpPacketTester("Hey man, How are you??", 1057));
        client.sendMessage(new KryoTestClass(100, 5, "HI THIS IS LONG", new SubClassTest("ok", 50)));

    }
}
