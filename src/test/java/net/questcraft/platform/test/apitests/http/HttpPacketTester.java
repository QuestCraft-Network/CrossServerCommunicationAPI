package net.questcraft.platform.test.apitests.http;

import net.questcraft.platform.handler.cscapi.annotations.HttpClassID;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;

@HttpClassID("HttpPacketTester")
public class HttpPacketTester implements HttpPacket {
    private String string;
    private int integer;

    public HttpPacketTester(String string, int integer) {
        this.string = string;
        this.integer = integer;
    }

    public HttpPacketTester() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return "HttpPacketTester{" +
                "string='" + string + '\'' +
                ", integer=" + integer +
                '}';
    }
}
