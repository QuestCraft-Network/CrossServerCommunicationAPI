package net.questcraft.platform.handler.cscapi.serializer.serializers.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public abstract class JsonPacketSerializer<T> extends StdSerializer<T> implements JsonPacketSerialization<T> {
    protected JsonPacketSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;
}
