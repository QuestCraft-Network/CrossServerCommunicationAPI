package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerialization {
    protected ObjectMapper objectMapper;

    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }
}
