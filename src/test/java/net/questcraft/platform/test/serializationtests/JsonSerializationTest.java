package net.questcraft.platform.test.serializationtests;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.SerializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.jsonserializer.JsonDeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.jsonserializer.JsonSerializationHandler;
import net.questcraft.platform.test.KryoTestClass;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;
import net.questcraft.platform.test.apitests.ws.SubClassTest;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JsonSerializationTest {
    @Test
    public void testSerialization() throws IOException, CSCException {
        String serialize = serialize();
        System.out.println(serialize);
    }

    private String serialize() throws IOException, CSCException {
        SerializationHandler handler = new JsonSerializationHandler();
        HttpPacketTester kryoTestClass = new HttpPacketTester("HEY", 10);
        return handler.serialize(kryoTestClass);
    }

    @Test
    public void testCustomSerializer() {
        DeserializationHandler serializationHandler = new JsonDeserializationHandler();
        serializationHandler.registerSerializer(SubClassTest.class, new CustomJsonSerializer());
    }

    @Test
    public void testDeserialization() throws IOException, CSCException {
        Set<Class<? extends Packet>> classSet = new HashSet<>();
        classSet.add(KryoTestClass.class);

        JsonDeserializationHandler deserializationHandler = new JsonDeserializationHandler();
        Packet deserialize = deserializationHandler.deserialize(this.serialize(), classSet);
    }

}
