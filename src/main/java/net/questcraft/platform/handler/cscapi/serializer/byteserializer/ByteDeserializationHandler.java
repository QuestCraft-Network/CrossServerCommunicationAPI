package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.io.Input;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.BytePacketSerializer;

import java.io.IOException;
import java.util.Set;

public class ByteDeserializationHandler extends ByteSerialization implements DeserializationHandler {

    public Packet deserialize(Object packet, Set<Class<?>> applicableClasses) throws IOException, CSCException {
        if (!(packet instanceof byte[])) throw new IllegalArgumentException("Packet Object must be of type byte[] to be deserializable by the ByteDeserializationHandler");
         byte[] bytePacket = (byte[]) packet;

        byte[] kryoBytes = this.removeClassID(bytePacket);
        Input input = new Input(kryoBytes);

        Object object = kryo.readObject(input, this.getClassType(bytePacket, applicableClasses));
        if (!(object instanceof Packet)) throw new IllegalArgumentException("Object type is not of type Packet");

        return (Packet) object;
    }

    private Class<?> getClassType(byte[] bytes, Set<Class<?>> applicableClasses) throws InvalidClassIDDescriptor {
        for (Class cls : applicableClasses) {
            if (this.isOfSameType(bytes, this.getSerializationKey(cls))) {
                kryo.register(cls);
                return cls;
            }
        }
        throw new IllegalArgumentException("Provided Byte Array matches no registered serializable class");
    }

    public byte[] removeClassID(byte[] bytes) throws IOException {
        ByteArrayBuffer baf = new ByteArrayBuffer(1);

        boolean pastSeparator = false;

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == ID_SEPARATOR) {
                pastSeparator = true;
            } else if (pastSeparator) baf.write(b);
        }
        return baf.getRawData();
    }

    public boolean isOfSameType(byte[] bytes, String id) {
        ByteArrayBuffer baf = new ByteArrayBuffer();

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == ID_SEPARATOR || id.getBytes().length < i) {
                byte[] classID = baf.getRawData();

                String byteID = new String(classID).trim();

                return byteID.equals(id);
            } else baf.write(b);
        }
        return false;
    }



    public void registerSerializer(Class<?> type, BytePacketSerializer<?> serializer) {
        this.kryo.register(type, serializer);
    }
}
