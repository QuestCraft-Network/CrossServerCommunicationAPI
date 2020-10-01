package net.questcraft.platform.handler.cscapi.serializer.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public abstract class BytePacketSerializer<T> extends Serializer<T> implements PacketSerializer {

    public abstract void write(Kryo kryo, Output output, T t);

    public abstract T read(Kryo kryo, Input input, Class<? extends T> aClass);
}
