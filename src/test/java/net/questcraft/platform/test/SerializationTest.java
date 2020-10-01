package net.questcraft.platform.test;

import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.WBPacket;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteDeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SerializationTest {
    @Test
    public void testSerialization() throws IOException, CSCException {
        byte[] bytes = serializeToBytes();
    }

    private byte[] serializeToBytes() throws IOException, CSCException {
        WBPacket packet = new KyroTestClass(1, 20, "COOLBUT");
        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());

        byte[] byteArray = serializer.serialize(packet);
        return byteArray;
    }

    @Test
    public void deserializeFromBytes() throws IOException, CSCException {
        byte[] bytes = serializeToBytes();
        DeserializationHandler handler = new ByteDeserializationHandler();
        Set<Class<?>> registeredClasses = new HashSet<Class<?>>();

        registeredClasses.add(KyroTestClass.class);

        WBPacket packet = handler.deserialize(bytes, registeredClasses);

        System.out.println(packet.toString());
    }

    @Test
    public void testBytePrint() {
        System.out.println(0x1D);
    }
}
