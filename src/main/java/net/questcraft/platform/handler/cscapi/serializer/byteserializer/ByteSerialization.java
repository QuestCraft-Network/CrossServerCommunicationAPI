package net.questcraft.platform.handler.cscapi.serializer.byteserializer;

import com.esotericsoftware.kryo.Kryo;
import net.questcraft.platform.handler.cscapi.annotations.ClassID;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.InvalidClassIDDescriptor;

import java.lang.annotation.Annotation;

public abstract class ByteSerialization {
    protected final Kryo kryo;

    protected static final byte ID_SEPARATOR = 0x1D;

    private static final Class<ClassID> TYPE_ID_ANNOTATION = ClassID.class;

    public ByteSerialization() {
        this.kryo = new Kryo();
    }

    private boolean hasAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
        return cls.isAnnotationPresent(annotation);
    }

    protected String getSerializationKey(Class<?> cls) throws InvalidClassIDDescriptor {
        if (!this.hasAnnotation(cls, TYPE_ID_ANNOTATION)) throw new IllegalArgumentException("Given Class has no annotation present for net/questcraft/platform/handler/cscapi/annotations/ClassID.java");

        ClassID classID = cls.getAnnotation(TYPE_ID_ANNOTATION);

        if (classID.value().isEmpty()) throw new InvalidClassIDDescriptor("Invalid ClassID, ClassID Cannot be empty");

        return classID.value();
    }
}
