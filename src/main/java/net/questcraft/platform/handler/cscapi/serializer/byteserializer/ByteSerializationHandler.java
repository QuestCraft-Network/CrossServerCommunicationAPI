package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.io.Output;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.WSPacket;
import net.questcraft.platform.handler.cscapi.serializer.serializers.BytePacketSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;



public class ByteSerializationHandler<T> extends ByteSerialization implements SerializationHandler<T> {
    private final Class<?> kryoClass;

    public ByteSerializationHandler(Class<?> kryoClass) {
        kryo.register(kryoClass);
        this.kryoClass = kryoClass;
    }

    public T serialize(Packet packet) throws IOException, CSCException {
        ByteArrayBuffer byteBuffer = new ByteArrayBuffer(1);

        byteBuffer.write(this.getSerializationKey(packet.getClass()).getBytes());
        byteBuffer.write(ID_SEPARATOR);

        Output output = new Output(new ByteArrayOutputStream(1));
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



    public void registerSerializer(Class<?> type, BytePacketSerializer<?> serializer) {
        this.kryo.register(type, serializer);
    }
}
