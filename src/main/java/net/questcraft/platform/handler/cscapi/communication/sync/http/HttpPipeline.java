package net.questcraft.platform.handler.cscapi.communication.sync.http;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import net.questcraft.platform.handler.cscapi.communication.Packet;
import net.questcraft.platform.handler.cscapi.communication.async.AsyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.communication.sync.SyncChannelPipeline;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import net.questcraft.platform.handler.cscapi.serializer.serializers.PacketSerializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public abstract class HttpPipeline implements SyncChannelPipeline {
    protected final HttpChannelHandler handler;
    protected final String KEY_VALUE;

    /**
     * Creates a instance of HttpPipeline from a Builder<?>
     *
     * @param builder The Builder to innit the properties from
     */
    protected HttpPipeline(Builder<?> builder) {
        this.handler = builder.handler;
        this.KEY_VALUE = builder.KEY_VALUE;
    }

    /**
     * A Abstract method for Pipelines to implement, Only used in Client onRegister
     * <p>
     * Should NOT be called by the user
     */
    public abstract void innit();


    @Override
    public void registerPacket(Class<? extends Packet> packet) {
        this.handler.registerPacket(packet);
    }

    @Override
    public <T> void registerSerializer(Class<T> cls, PacketSerializer<T> serializer) {
        this.handler.registerSerializer(cls, serializer);
    }

    /**
     * The Builder for all Children of HttpPipeline
     *
     * @param <T> The Child class that the Builder will be building
     */
    public static class Builder<T extends HttpPipeline> extends AsyncChannelPipeline.Builder<T> {
        private HttpChannelHandler handler;
        protected String KEY_VALUE = "value";

        public Builder(Class<T> pipeCls) {
            super(pipeCls);
        }

        public Builder KEY_VALUE(String keyVal) {
            this.KEY_VALUE = keyVal;
            return this;
        }

        @Override
        public T build(CommunicationHandler handler) throws CSCInstantiationException {
            try {
                if (!HttpPipeline.class.isAssignableFrom(this.pipeCls))
                    throw new IllegalArgumentException("Class is not of type HttpPipeline, To be registered as a Pipeline this must be the case");

                if (!(handler instanceof HttpChannelHandler))
                    throw new IllegalArgumentException("ChannelHandler must be of type HttpChannelHandler");
                this.handler = (HttpChannelHandler) handler;

                Constructor<T> constructor = this.pipeCls.getConstructor(HttpPipeline.Builder.class);
                return constructor.newInstance(this);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new CSCInstantiationException("Failed to instantiate HttpPipeline of type : " + this.pipeCls.toString());
            }
        }
    }
}
