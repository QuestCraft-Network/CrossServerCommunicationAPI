package net.questcraft.platform.test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class KyroTesters {
    @Test
    public void testKyro() {
        Kryo kryo = new Kryo();
        kryo.register(KryoTestClass.class);

        KryoTestClass test = new KryoTestClass(1, 1, "asdf", null);
        Output output = new Output(new ByteArrayOutputStream());
        kryo.writeObject(output, test);
        output.flush();
        output.close();

        Input input = new Input(output.getBuffer());
        KryoTestClass object2 = kryo.readObject(input, KryoTestClass.class);

        System.out.println(object2);
    }


}
