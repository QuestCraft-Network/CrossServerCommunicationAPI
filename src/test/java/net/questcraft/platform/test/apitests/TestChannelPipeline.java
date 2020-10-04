package net.questcraft.platform.test.apitests;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import org.eclipse.jetty.websocket.api.Session;

public class TestChannelPipeline extends SocketPipeline {
    public TestChannelPipeline(String path) {
        super(path);
    }

    @Override
    public void onMessage(Packet packet) {
        System.out.println(packet.toString());
    }

    @Override
    public void onClose(int statusCode, String reason) {
        super.onClose(statusCode, reason);
        System.out.println("closed");
    }

    @Override
    public void onConnect(Session session) {
        super.onConnect(session);
        System.out.println("Connected");
//        try {
////            this.sendMessage(new KryoTestClass(1, 5, "OK"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CSCException e) {
//            e.printStackTrace();
//        }
    }
}
