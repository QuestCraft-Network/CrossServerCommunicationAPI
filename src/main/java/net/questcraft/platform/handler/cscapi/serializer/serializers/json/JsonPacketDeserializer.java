package net.questcraft.platform.handler.cscapi.serializer.serializers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public abstract class JsonPacketDeserializer<T> extends StdDeserializer<T> implements JsonPacketSerialization<T> {
    protected JsonPacketDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public abstract T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException;
}
