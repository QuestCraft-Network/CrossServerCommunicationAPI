package net.questcraft.platform.test.serializationtests;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.questcraft.platform.handler.cscapi.serializer.serializers.json.JsonPacketSerializer;
import net.questcraft.platform.test.apitests.ws.SubClassTest;

import java.io.IOException;

public class CustomJsonSerializer extends JsonPacketSerializer<SubClassTest> {
    protected CustomJsonSerializer(Class t) {
        super(t);
    }

    public CustomJsonSerializer() {
        super(null);
    }


    @Override
    public void serialize(SubClassTest subClassTest, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
