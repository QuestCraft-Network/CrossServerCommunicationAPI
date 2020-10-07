package net.questcraft.platform.handler.cscapi.communication.messagebuffer;

import net.questcraft.platform.handler.cscapi.communication.Packet;


public class PacketMessageBuffer extends MessageBuffer {
    /**
     * Write a packet to the Packet Message buffer
     *
     * @param packet The Packet to write into the Buffer
     */
    @Override
    public void write(Packet packet) {
        int assumedSize = this.size + 1;
        this.ensureSize(assumedSize);
        this.packetBuffer[assumedSize - 1] = packet;
        this.size = assumedSize;
    }

    /**
     * Write a array of packets into the  Packet Message buffer
     *
     * @param packets The Packets to write into the Buffer
     */
    @Override
    public void write(Packet[] packets) {
        int assumedSize = this.size + packets.length;
        this.ensureSize(assumedSize);
        System.arraycopy(packets, 0 , this.packetBuffer, 0, assumedSize);
        this.size = assumedSize;
    }

    /**
     * Will return the packet buffer
     *
     * @return All packets in the buffer
     */
    @Override
    public Packet[] toPacketArray() {
        return this.packetBuffer;
    }

    /**
     * empty's the buffer
     */
    @Override
    public void empty() {
        this.size = 0;
        this.packetBuffer = new Packet[1];
    }

    /**
     * Ensures that the buffer has enough space to contain as many elements as specified in the param size
     *
     * @param size The size to ensure
     */
    private void ensureSize(int size) {
        if (size > this.size) {
            Packet[] expectedBuf = new Packet[size];
            System.arraycopy(this.packetBuffer, 0, expectedBuf, 0, this.size);
            this.packetBuffer = expectedBuf;
        }
    }
}
