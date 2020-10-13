package net.questcraft.platform.test.apitests.ws;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class TestChannelPipeline extends SocketPipeline {


    public TestChannelPipeline(Builder builder) {
        super(builder);
    }

    @Override
    public void onMessage(Packet packet) {
        System.out.println(packet.toString());
        try {
            this.sendMessage(new KryoTestClass(10, 5, "I SENT THIS BACK", new SubClassTest("OK YAY", 1)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSCException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int statusCode, String reason) {
        super.onClose(statusCode, reason);
        System.out.println(statusCode + " and the reason " + reason);
        System.out.println("closed");
    }

    @Override
    public void onConnect(Session session) {
        super.onConnect(session);
    }

}
