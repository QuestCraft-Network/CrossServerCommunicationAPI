package net.questcraft.platform.test;

import net.questcraft.platform.handler.cscapi.annotations.ClassID;
import net.questcraft.platform.handler.cscapi.communication.websocket.WSPacket;

@ClassID("KyroTestClass")
public class KryoTestClass implements WSPacket {
    private int intOne;
    private Integer integerTwo;
    String string;

    public KryoTestClass(int intOne, Integer integerTwo, String string) {
        this.intOne = intOne;
        this.integerTwo = integerTwo;
        this.string = string;
    }

    public KryoTestClass() {
    }

    public int getIntOne() {
        return intOne;
    }

    public void setIntOne(int intOne) {
        this.intOne = intOne;
    }

    public Integer getIntegerTwo() {
        return integerTwo;
    }

    public void setIntegerTwo(Integer integerTwo) {
        this.integerTwo = integerTwo;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }


    @Override
    public String toString() {
        return "KyroTestClass{" +
                "intOne=" + intOne +
                ", integerTwo=" + integerTwo +
                ", string='" + string + '\'' +
                '}';
    }
}
