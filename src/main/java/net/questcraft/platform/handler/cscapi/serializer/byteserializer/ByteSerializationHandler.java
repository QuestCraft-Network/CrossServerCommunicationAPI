package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.io.Output;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.byteserializers.BytePacketSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


public class ByteSerializationHandler extends ByteSerialization implements SerializationHandler {

    public byte[] serialize(Packet packet) throws IOException, CSCException {
        ByteArrayDataOutput byteBuffer =  ByteStreams.newDataOutput(1);

        byteBuffer.write(this.getSerializationKey(packet.getClass()).getBytes());
        byteBuffer.write(ID_SEPARATOR);

        Output output = new Output(new ByteArrayOutputStream(1));

        kryo.register(packet.getClass());
        this.registerMemberVariables(packet.getClass());
        this.kryo.writeObject(output, packet);
        output.flush();
        byteBuffer.write(this.trimByteArray(output.getBuffer()));
        output.close();


        return byteBuffer.toByteArray();
    }

    private byte[] trimByteArray(byte[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            byte b = array[i];
            if (!(b == 0x00)) return Arrays.copyOfRange(array, 0, i + 1);
        }
        throw new IllegalArgumentException("Array cannot be of null objects");
    }


    @Override
    public <T> void registerSerializer(Class<T> type, PacketSerializer<T> serializer) throws IllegalArgumentException {
        register(type, serializer);
    }

    @Override
    public <T> void registerSerializer(Map<Class<T>, PacketSerializer<T>> serializers) throws IllegalArgumentException {
        for (Class<T> cls : serializers.keySet()) {
           register(cls, serializers.get(cls));
        }
    }

    private <T> void register(Class<T> cls, PacketSerializer<T> serializer) {
        if (!(serializer instanceof BytePacketSerializer)) throw new IllegalArgumentException("Type must be of BytePacketSerializer");
        BytePacketSerializer<T> packetSerializer = (BytePacketSerializer<T>) serializer;
        this.kryo.register(cls, packetSerializer);
    }
}
