package net.questcraft.platform.test;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.communication.websocket.WSPacket;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteDeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteSerializationHandler;
import net.questcraft.platform.test.apitests.SubClassTest;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class SerializationTest {
    @Test
    public void testSerialization() throws IOException, CSCException {
        byte[] bytes = serializeToBytes();
    }

    private byte[] serializeToBytes() throws IOException, CSCException {
        WSPacket packet = new KryoTestClass();
        SerializationHandler<byte[]> serializer = new ByteSerializationHandler(packet.getClass());

        byte[] byteArray = serializer.serialize(packet);
        return byteArray;
    }

    @Test
    public void deserializeFromBytes() throws IOException, CSCException {
        byte[] bytes = serializeToBytes();
        DeserializationHandler handler = new ByteDeserializationHandler();
        Set<Class<?>> registeredClasses = new HashSet<Class<?>>();

        registeredClasses.add(KryoTestClass.class);

        Packet packet = handler.deserialize(bytes, registeredClasses);

        System.out.println(packet.toString());
    }

    @Test
    public void testBytePrint() {
        System.out.println(0x1D);
    }

    @Test
    public void primitiveClassType() throws IllegalAccessException {
        SubClassTest test = new SubClassTest("asdf", 5);
        for (Field field : SubClassTest.class.getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers())) {
                System.out.println(field.getType());
                System.out.println(field.get(test));
            }
        }
    }
}
