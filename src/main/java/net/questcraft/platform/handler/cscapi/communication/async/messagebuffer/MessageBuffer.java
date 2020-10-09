package net.questcraft.platform.handler.cscapi.communication.async.messagebuffer;

import net.questcraft.platform.handler.cscapi.communication.async.Packet;

public abstract class MessageBuffer {
    //TODO create a MessageBuffer#flush() method for flushing contents of the buffer to a InputStream or Packet[]

    /**
     * The packet Buffer
     */
    protected Packet[] packetBuffer;

    /**
     * Current size of the packet Buffer
     */
    protected int size = 0;

    /**
     * Default constructor
     */
    public MessageBuffer() {
        this.packetBuffer = new Packet[1];
    }

    /**
     * Create a MessageBuffer with the optional size of a buffer
     *
     * @param size Optional size of initial Buffer
     */
    public MessageBuffer(int size) {
        this.packetBuffer = new Packet[size];
    }

    /**
     * Forces child classes to provided an implementation for writing to the packetBuffer
     *
     * @param packet The packet to write into the buffer
     */
    public abstract void write(Packet packet);

    /**
     * Forces child classes to provided an implementation for writing multiple packets to the packetBuffer
     *
     * @param packets The packets to write into the buffer
     */
    public abstract void write(Packet[] packets);

    /**
     * Forces Child classes to provided a implementation for getting the packetBuffer
     *
     * @return The Packet Buffer
     */
    public abstract Packet[] toPacketArray();

    /**
     * Forces Child classes to provided a implementation for emptying the packetBuffer
     */
    public abstract void empty();

    /**
     * Checks if the size of the buffer is 0(empty)
     *
     * @return boolean of is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

}
