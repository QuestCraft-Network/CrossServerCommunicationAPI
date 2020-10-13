package net.questcraft.platform.test.sparkcache;

import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;

public class TestWSCache extends SparkCacheHandler.SparkCache {
    public TestWSCache() {
        super(2);
    }

    @Override
    public void register() {
        System.out.println("WebSocket initialised");
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
