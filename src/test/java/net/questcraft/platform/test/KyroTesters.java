package net.questcraft.platform.test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class KyroTesters {
    @Test
    public void testKyro() {
        Kryo kryo = new Kryo();
        kryo.register(KyroTestClass.class);

        KyroTestClass test = new KyroTestClass(1, 5, "HEY mann");
        Output output = new Output(new ByteArrayOutputStream());
        kryo.writeObject(output, test);
        output.flush();
        output.close();

        Input input = new Input(output.getBuffer());
        KyroTestClass object2 = kryo.readObject(input, KyroTestClass.class);

        System.out.println(object2);
    }


}
