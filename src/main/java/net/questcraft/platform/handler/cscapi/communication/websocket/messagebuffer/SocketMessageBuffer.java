package net.questcraft.platform.handler.cscapi.communication.websocket.messagebuffer;

import net.questcraft.platform.handler.cscapi.communication.Packet;


public class SocketMessageBuffer extends MessageBuffer {
    @Override
    public void write(Packet packet) {
        int assumedSize = this.size + 1;
        this.ensureSize(assumedSize);
        this.packetBuffer[assumedSize - 1] = packet;
        this.size = assumedSize;
    }

    @Override
    public void write(Packet[] packets) {
        int assumedSize = this.size + packets.length;
        this.ensureSize(assumedSize);
        System.arraycopy(packets, 0 , this.packetBuffer, 0, assumedSize);
        this.size = assumedSize;
    }

    @Override
    public Packet[] toPacketArray() {
        return this.packetBuffer;
    }

    @Override
    public void empty() {
        this.size = 0;
        this.packetBuffer = new Packet[1];
    }

    private void ensureSize(int size) {
        if (size > this.size) {
            Packet[] expectedBuf = new Packet[size];
            System.arraycopy(this.packetBuffer, 0, expectedBuf, 0, this.size);
            this.packetBuffer = expectedBuf;
        }
    }
}
