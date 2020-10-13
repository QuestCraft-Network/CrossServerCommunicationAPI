package net.questcraft.platform.handler.cscapi.communication.spark.cache;

import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.sync.http.HttpPacket;
import net.questcraft.platform.handler.cscapi.communication.sync.http.error.HttpPacketError;
import net.questcraft.platform.handler.cscapi.communication.sync.http.server.HttpRoute;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.PacketTypeMismatchException;
import spark.Spark;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpCache extends SparkCacheHandler.SparkCache {
    private final HttpRoute route;
    private final String path;
    private final String KEY_VALUE;

    private final HttpChannelHandler handler;

    /**
     * Constructs a new HttpCache through its Builder
     *
     * @param builder The Builder to construct from
     */
    public HttpCache(Builder builder) {
        super(1);
        this.route = builder.route;
        this.path = builder.path;
        this.KEY_VALUE = builder.KEY_VALUE;

        this.handler = builder.httpChannelHandler;
    }

    /**
     * Registers the HttpRoute through Spark
     */
    @Override
    public void register() {
        Spark.get(path, ((request, response) -> {
            try {
                String s = this.decode(request.queryParams(KEY_VALUE));
                Packet deserialize = this.handler.getDeserializationHandler().deserialize(s, this.handler.getRegisteredClasses());

                if (!(deserialize instanceof HttpPacket))
                    throw new PacketTypeMismatchException("Packet type must be of type HttpPacket");

                HttpPacket packet = route.handle((HttpPacket) deserialize, response);
                return this.handler.getSerializationHandler().<String>serialize(packet);

            } catch (Exception | CSCException e) {
                try {
                    this.handler.registerPacket(HttpPacketError.class);
                    return this.handler.getSerializationHandler().<String>serialize(new HttpPacketError("Error handling GET Route, Message: " + e.getMessage()));
                } catch (CSCException cscException) {
                    throw new RuntimeException("Error serializing Error message, Original Message: " + cscException.getMessage() + ", Serialization Error: " + e.getMessage());
                }
            }
        }));
    }

    private String decode(String str) {
        try {
            return URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger("HttpCache").log(Level.WARNING, "Failed to Decode String: " + str);
            return str;
        }
    }

    /**
     * Overridden equals method
     *
     * @param o The object to check against
     * @return boolean of does equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpCache httpCache = (HttpCache) o;
        return Objects.equals(route, httpCache.route) &&
                Objects.equals(path, httpCache.path);
    }

    public static class Builder {
        private final HttpRoute route;
        private final String path;
        private final String KEY_VALUE;

        private final HttpChannelHandler httpChannelHandler;

        public Builder(String path, HttpRoute route, String KEY_VALUE, HttpChannelHandler httpChannelHandler) {
            this.route = route;
            this.KEY_VALUE = KEY_VALUE;
            this.httpChannelHandler = httpChannelHandler;
            this.path = path;
        }

        public HttpCache build() {
            return new HttpCache(this);
        }
    }

}
