package net.questcraft.platform.test;

import net.questcraft.platform.handler.cscapi.communication.sync.http.client.HttpURL;
import org.junit.Test;

import java.io.IOException;

public class GeneralTests {
    @Test
    public void testHttpURL() throws IOException {
        HttpURL url = new HttpURL.Builder().path("http://questcraft.net").build();
        System.out.println(url.connect());
    }


}
