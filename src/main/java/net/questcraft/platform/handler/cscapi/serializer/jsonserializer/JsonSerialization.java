package net.questcraft.platform.handler.cscapi.serializer.jsonserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.questcraft.platform.handler.cscapi.annotations.HttpClassID;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;

import java.lang.annotation.Annotation;

public class JsonSerialization {
    private static final Class<HttpClassID> TYPE_ID_ANNOTATION = HttpClassID.class;
    protected final ObjectMapper objectMapper;

    protected final static String NAME_IDENTIFIER = "ClassID";
    protected final static String[] NAME_WRAPPER = {"(", ")"};


    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }

    private boolean hasAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
        return cls.isAnnotationPresent(annotation);
    }

    protected String getSerializationKey(Class<?> cls) throws InvalidClassIDDescriptor {
        if (!this.hasAnnotation(cls, TYPE_ID_ANNOTATION)) throw new IllegalArgumentException("Given Class has no annotation present for net/questcraft/platform/handler/cscapi/annotations/HttpClassID.java");

        HttpClassID classID = cls.getAnnotation(TYPE_ID_ANNOTATION);

        if (classID.value().isEmpty()) throw new InvalidClassIDDescriptor("Invalid ClassID, ClassID Cannot be empty");

        return classID.value();
    }
}
