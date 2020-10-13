package net.questcraft.platform.handler.cscapi.communication.spark.cache;

import spark.Spark;

import java.util.HashSet;
import java.util.Set;

/**
 * The Handler for all SparkCaches
 *
 * @author Chestly
 */
public class SparkCacheHandler {
    private static final Set<SparkCache> cacheSet = new HashSet<>();
    private static boolean cache = false;

    private SparkCacheHandler() {
    }

    /**
     * Sets the Value of member variable cache
     *
     * @param doCache boolean of whether the system should cache or not
     */
    public static void cache(boolean doCache) {
        cache = doCache;
    }

    /**
     * Writes a SparkCache to the Cache
     *
     * @param sparkCache The SparkCache to write
     */
    public static void writeCache(SparkCache sparkCache) {
        if (!cache) sparkCache.register();
        else cacheSet.add(sparkCache);
    }

    /**
     * Deploys all the cached caches in the order of most importance
     */
    public static void deploy() {
        executeCache(cacheSet);
        Spark.init();
    }

    /**
     * The Private recursive method to execute the goal of each cache
     *
     * @param caches The Set of caches to recursively execute
     */
    private static void executeCache(Set<SparkCache> caches) {
        Set<SparkCache> caches2 = new HashSet<>();

        int mostImportant = -1;
        for (SparkCache cache : caches) {
            if (mostImportant == -1 || mostImportant < cache.importance) mostImportant = cache.importance;
        }

        for (SparkCache cache : caches) {
            if (cache.importance != mostImportant) caches2.add(cache);
            else cache.register();
        }
        if (caches2.size() != 0) executeCache(caches2);
    }

    public static abstract class SparkCache {
        private final int importance;

        public SparkCache(int importance) {
            this.importance = importance;
        }

        public abstract void register();

        @Override
        public abstract boolean equals(Object obj);
    }
}
