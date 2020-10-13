package net.questcraft.platform.test.apitests.all;

import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.test.KryoTestClass;
import net.questcraft.platform.test.apitests.http.HttpPacketTester;
import net.questcraft.platform.test.apitests.http.server.ServerHttpTester;
import net.questcraft.platform.test.apitests.http.server.TestHttpServerPipeline;
import net.questcraft.platform.test.apitests.ws.TestChannelPipeline;
import net.questcraft.platform.test.apitests.ws.TestWSServer;
import spark.Spark;

public class AllServer {
    public static void main(String[] args) throws CSCException, Exception {
        Spark.port(8000);

        SparkCacheHandler.cache(true);

        TestHttpServerPipeline httpPipeline = ServerHttpTester.createServer();

        TestChannelPipeline socketPipeline = TestWSServer.createWSServer();

        SparkCacheHandler.deploy();

        httpPipeline.registerPacket(HttpPacketTester.class);
        socketPipeline.registerPacket(KryoTestClass.class);

    }
}
