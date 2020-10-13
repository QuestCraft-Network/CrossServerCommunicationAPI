package net.questcraft.platform.test.apitests.http.server;

import net.questcraft.platform.handler.cscapi.communication.sync.http.server.HttpServerPipeline;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;

public class TestHttpServerPipeline extends HttpServerPipeline {
    public TestHttpServerPipeline(Builder builder) {
        super(builder);
    }

    @Override
    public void innit() {
        System.out.println("innit has been CALLED");
        registerGet("/doTHIS", ((packet, response) -> {
            System.out.println(packet.toString());
            System.out.println("SOMETHING????????");
            return new HttpPacketTester("HI BACK", 27);
        }));
    }
}
