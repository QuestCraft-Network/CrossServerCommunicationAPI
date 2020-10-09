package net.questcraft.platform.test;

import net.questcraft.platform.handler.cscapi.annotations.SocketClassID;
import net.questcraft.platform.handler.cscapi.communication.async.websocket.WSPacket;
import net.questcraft.platform.test.apitests.SubClassTest;

@SocketClassID("KyroTestClass")
public class KryoTestClass implements WSPacket {
    private int intOne;
    private Integer integerTwo;
    transient String string;
    private SubClassTest test;

    public KryoTestClass(int intOne, Integer integerTwo, String string, SubClassTest test) {
        this.intOne = intOne;
        this.integerTwo = integerTwo;
        this.string = string;
        this.test = test;
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

//    public SubClassTest getTest() {
//        return test;
//    }
//
//    public void setTest(SubClassTest test) {
//        this.test = test;
//    }


    @Override
    public String toString() {
        return "KryoTestClass{" +
                "intOne=" + intOne +
                ", integerTwo=" + integerTwo +
                ", string='" + string + '\'' +
                ", test=" + test +
                '}';
    }
}
