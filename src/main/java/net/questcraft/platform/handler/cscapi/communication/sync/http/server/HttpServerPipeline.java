package net.questcraft.platform.handler.cscapi.communication.sync.http.server;

import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPipeline;
import net.questcraft.platform.handler.cscapi.communication.spark.cache.HttpCache;
import net.questcraft.platform.handler.cscapi.communication.spark.cache.SparkCacheHandler;

public abstract class HttpServerPipeline extends HttpPipeline {
    protected HttpServerPipeline(Builder builder) {
        super(builder);
    }

    protected void registerGet(String path, HttpRoute route) {
        HttpCache cache = new HttpCache.Builder(path, route, KEY_VALUE, this.handler).build();
        SparkCacheHandler.writeCache(cache);
    }

    @Override
    public abstract void innit();
}
