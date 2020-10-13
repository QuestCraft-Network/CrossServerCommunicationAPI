package net.questcraft.platform.handler.cscapi.communication.sync.http.client;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.PacketTypeMismatchException;

import java.io.IOException;

public abstract class HttpClientPipeline extends HttpPipeline {
    protected HttpClientPipeline(Builder builder) {
        super(builder);
    }

    public HttpPacket sendMessage(String path, HttpPacket packet) throws IOException, CSCException {
        String serialize = this.handler.getSerializationHandler().serialize(packet);

        HttpURL url = new HttpURL.Builder().path(path).param(KEY_VALUE, serialize).build();

        String res = url.connect();

        Packet resPacket = this.handler.getDeserializationHandler().deserialize(res, this.handler.getRegisteredClasses());
        if (!(resPacket instanceof HttpPacket)) throw new PacketTypeMismatchException("Packet type must be of type HttpPacket");

        return (HttpPacket) resPacket;
    }

    @Override
    public void innit() { /**Unused**/ }
}
