package net.questcraft.platform.handler.cscapi.communication.websocket.messagebuffer;

import net.questcraft.platform.handler.cscapi.communication.Packet;

public abstract class MessageBuffer {
    protected Packet[] packetBuffer;

    protected int size = 0;

    public MessageBuffer() {
        this.packetBuffer = new Packet[1];
    }

    public MessageBuffer(int size) {
        this.packetBuffer = new Packet[size];
    }

    public abstract void write(Packet packet);

    public abstract void write(Packet[] packets);

    public abstract Packet[] toPacketArray();

    public abstract void empty();

    public boolean isEmpty() {
        return this.size == 0;
    }

}
