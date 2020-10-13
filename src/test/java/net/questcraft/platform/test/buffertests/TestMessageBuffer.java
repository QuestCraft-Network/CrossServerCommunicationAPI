package net.questcraft.platform.test.buffertests;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.async.messagebuffer.MessageBuffer;
import net.questcraft.platform.handler.cscapi.communication.async.messagebuffer.PacketMessageBuffer;
import net.questcraft.platform.test.KryoTestClass;
import org.junit.Test;

public class TestMessageBuffer {
    @Test
    public void testBuffer() {
        MessageBuffer buffer = new PacketMessageBuffer();

        Packet packet = new KryoTestClass();
        Packet packet2 = new KryoTestClass();
        Packet packet3 = new KryoTestClass();
        Packet packet4 = new KryoTestClass();
        Packet packet5 = new KryoTestClass();

        buffer.write(packet);
        buffer.write(packet2);
        buffer.write(packet3);
        buffer.write(packet4);
        buffer.write(packet5);

        for (Packet packet1 : buffer.toPacketArray()) {
           System.out.println(packet1);
       }
    }
}
