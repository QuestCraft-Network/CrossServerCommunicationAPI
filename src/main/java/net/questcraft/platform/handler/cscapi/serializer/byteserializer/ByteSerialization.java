package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.Kryo;
import net.questcraft.platform.handler.cscapi.annotations.SocketClassID;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ByteSerialization {
    protected final Kryo kryo;

    protected static final byte ID_SEPARATOR = 0x1D;

    private static final Class<SocketClassID> TYPE_ID_ANNOTATION = SocketClassID.class;

    public ByteSerialization() {
        this.kryo = new Kryo();
    }

    private boolean hasAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
        return cls.isAnnotationPresent(annotation);
    }

    protected String getSerializationKey(Class<?> cls) throws InvalidClassIDDescriptor {
        if (!this.hasAnnotation(cls, TYPE_ID_ANNOTATION)) throw new IllegalArgumentException("Given Class has no annotation present for net/questcraft/platform/handler/cscapi/annotations/ClassID.java");

        SocketClassID classID = cls.getAnnotation(TYPE_ID_ANNOTATION);

        if (classID.value().isEmpty()) throw new InvalidClassIDDescriptor("Invalid ClassID, ClassID Cannot be empty");

        return classID.value();
    }

    protected void registerMemberVariables(Class cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            Class<?> fieldType = field.getType();
            if (!fieldType.isPrimitive() && !Modifier.isTransient(field.getModifiers())) {
                this.kryo.register(fieldType);
            }
        }
    }
}
