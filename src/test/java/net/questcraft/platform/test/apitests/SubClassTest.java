package net.questcraft.platform.test.apitests;

public class SubClassTest {
    public String ok;
    public int integerTHing;

    public SubClassTest(String ok, int integerTHing) {
        this.ok = ok;
        this.integerTHing = integerTHing;
    }

    public SubClassTest() {
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public int getIntegerTHing() {
        return integerTHing;
    }

    public void setIntegerTHing(int integerTHing) {
        this.integerTHing = integerTHing;
    }

    @Override
    public String toString() {
        return "SubClassTest{" +
                "ok='" + ok + '\'' +
                ", integerTHing=" + integerTHing +
                '}';
    }
}
