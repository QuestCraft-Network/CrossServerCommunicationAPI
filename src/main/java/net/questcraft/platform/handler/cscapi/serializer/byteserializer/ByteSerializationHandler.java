package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.io.Output;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.BytePacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


public class ByteSerializationHandler<T> extends ByteSerialization implements SerializationHandler<T> {
    private final Class<?> kryoClass;

    public ByteSerializationHandler(Class<?> kryoClass) {
        this.kryoClass = kryoClass;
    }

    public T serialize(Packet packet) throws IOException, CSCException {
        ByteArrayBuffer byteBuffer = new ByteArrayBuffer(1);

        byteBuffer.write(this.getSerializationKey(packet.getClass()).getBytes());
        byteBuffer.write(ID_SEPARATOR);

        Output output = new Output(new ByteArrayOutputStream(1));

        kryo.register(kryoClass);
        this.registerMemberVariables(kryoClass);
        this.kryo.writeObject(output, packet);
        output.flush();
        byteBuffer.write(this.trimByteArray(output.getBuffer()));
        output.close();


        return (T) byteBuffer.getRawData();
    }

    private byte[] trimByteArray(byte[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            byte b = array[i];
            if (!(b == 0x00)) return Arrays.copyOfRange(array, 0, i + 1);
        }
        throw new IllegalArgumentException("Array cannot be of null objects");
    }

    public void registerSerializer(Class<?> type, PacketSerializer serializer) throws IllegalArgumentException {
        if (!(serializer instanceof BytePacketSerializer)) throw new IllegalArgumentException("Type must be of BytePacketSerializer");
        BytePacketSerializer packetSerializer = (BytePacketSerializer) serializer;
        this.kryo.register(type, packetSerializer);
    }

    @Override
    public void registerSerializer(Map<Class<?>, PacketSerializer> serializers) throws IllegalArgumentException {
        for (Class<?> cls : serializers.keySet()) {
            if (!(serializers.get(cls) instanceof BytePacketSerializer)) throw new IllegalArgumentException("Type must be of BytePacketSerializer");
            BytePacketSerializer packetSerializer = (BytePacketSerializer) serializers.get(cls);
            this.kryo.register(cls, packetSerializer);
        }
    }
}
