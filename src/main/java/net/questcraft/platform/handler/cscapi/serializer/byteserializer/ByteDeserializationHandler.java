package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.io.Input;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;
import net.questcraft.platform.handler.cscapi.serializer.serializers.byteserializers.BytePacketSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class ByteDeserializationHandler extends ByteSerialization implements DeserializationHandler {
    public Packet deserialize(Object packet, Set<Class<? extends Packet>> applicableClasses) throws IOException, CSCException {
        if (!(packet instanceof byte[])) throw new IllegalArgumentException("Packet Object must be of type byte[] to be deserializable by the ByteDeserializationHandler");
         byte[] bytePacket = (byte[]) packet;


        byte[] kryoBytes = this.removeClassID(bytePacket);
        Input input = new Input(kryoBytes);

        return kryo.readObject(input, this.getClassType(bytePacket, applicableClasses));
    }

    private Class<? extends Packet> getClassType(byte[] bytes, Set<Class<? extends Packet>> applicableClasses) throws InvalidClassIDDescriptor {
        for (Class<? extends Packet> cls : applicableClasses) {
            if (this.isOfSameType(bytes, this.getSerializationKey(cls))) {
                this.kryo.register(cls);
                this.registerMemberVariables(cls);

                return cls;
            }
        }
        throw new IllegalArgumentException("Provided Byte Array matches no registered serializable class");
    }

    public byte[] removeClassID(byte[] bytes) throws IOException {
        ByteArrayDataOutput baf =  ByteStreams.newDataOutput(1);


        boolean pastSeparator = false;

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == ID_SEPARATOR) {
                pastSeparator = true;
            } else if (pastSeparator) baf.write(b);
        }
        return baf.toByteArray();
    }

    public boolean isOfSameType(byte[] bytes, String id) {
        ByteArrayDataOutput baf =  ByteStreams.newDataOutput(1);

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == ID_SEPARATOR || id.getBytes().length < i) {
                byte[] classID = baf.toByteArray();

                String byteID = new String(classID).trim();

                return byteID.equals(id);
            } else baf.write(b);
        }
        return false;
    }


    @Override
    public <T> void registerSerializer(Class<T> type, PacketSerializer<T> serializer) throws IllegalArgumentException {
        if (!(serializer instanceof BytePacketSerializer)) throw new IllegalArgumentException("Type must be of BytePacketSerializer");
        BytePacketSerializer<T> packetSerializer = (BytePacketSerializer<T>) serializer;
        this.kryo.register(type, packetSerializer);
    }

    @Override
    public <T> void registerSerializer(Map<Class<T>, PacketSerializer<T>> serializers) throws IllegalArgumentException {
        for (Class<?> cls : serializers.keySet()) {
            if (!(serializers.get(cls) instanceof BytePacketSerializer)) throw new IllegalArgumentException("Type must be of BytePacketSerializer");
            BytePacketSerializer<T> packetSerializer = (BytePacketSerializer<T>) serializers.get(cls);
            this.kryo.register(cls, packetSerializer);
        }
    }
}
