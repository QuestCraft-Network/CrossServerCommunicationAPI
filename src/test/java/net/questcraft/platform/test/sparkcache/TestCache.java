package net.questcraft.platform.test.sparkcache;

import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;
import org.junit.Test;

public class TestCache {
    @Test
    public void testSparkCache() {
        TestHttpCache httpCache = new TestHttpCache("", ((packet, response) -> {
            System.out.println("CALLED YES");
            return null;
        }));
        TestWSCache testWSCache = new TestWSCache();
        SparkCacheHandler.cache(true);

        SparkCacheHandler.writeCache(httpCache);
        SparkCacheHandler.writeCache(testWSCache);

        SparkCacheHandler.deploy();
    }
}
