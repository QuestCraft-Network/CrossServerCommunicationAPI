package net.questcraft.platform.test.sparkcache;

import net.questcraft.platform.handler.cscapi.communication.sync.http.server.HttpRoute;
import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;

public class TestHttpCache extends SparkCacheHandler.SparkCache {
    private final String path;
    private final HttpRoute route;

    public TestHttpCache(String path, HttpRoute route) {
        super(1);
        this.path = path;
        this.route = route;
    }

    @Override
    public void register() {
        route.handle(null, null);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
