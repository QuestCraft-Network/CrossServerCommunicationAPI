package net.questcraft.platform.handler.cscapi.communication.websocket;

import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;
import org.eclipse.jetty.websocket.api.Session;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SocketPipelineFactory {

    public static TempSocketPipeline createTemp(String path) {
        return new TempSocketPipeline(path);
    }

    protected static class TempSocketPipeline extends SocketPipelineFactory {
        private final String path;

        protected TempSocketPipeline(String path) {
            this.path = path;
        }

        public SocketPipeline registerPipeline(Class<? extends SocketPipeline> pipeline, Session session) throws CSCInstantiationException {
            try {
                Constructor<? extends SocketPipeline> constructor = pipeline.getConstructor(String.class, Session.class);
                return constructor.newInstance(this.path, session);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new CSCInstantiationException("Cannot instantiate " + pipeline.toString() + ". Error : " + e.getMessage());
            }
        }
    }
}
