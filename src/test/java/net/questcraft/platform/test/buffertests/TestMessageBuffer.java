package net.questcraft.platform.test.buffertests;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.messagebuffer.MessageBuffer;
import net.questcraft.platform.handler.cscapi.communication.messagebuffer.PacketMessageBuffer;
import net.questcraft.platform.test.KryoTestClass;
import org.junit.Test;

public class TestMessageBuffer {
    @Test
    public void testBuffer() {
        MessageBuffer buffer = new PacketMessageBuffer();

        Packet packet = new KryoTestClass(1, 1, "");
        Packet packet2 = new KryoTestClass(5, 1, "");
        Packet packet3 = new KryoTestClass(56, 1, "");
        Packet packet4 = new KryoTestClass(3124, 1, "");
        Packet packet5 = new KryoTestClass(2, 1, "");

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
