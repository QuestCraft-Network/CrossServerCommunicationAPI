package net.questcraft.platform.handler.cscapi.communication;

import net.questcraft.platform.handler.cscapi.communication.websocket.SocketPipeline;
import net.questcraft.platform.handler.cscapi.serializer.DeserializationHandler;
import net.questcraft.platform.handler.cscapi.serializer.WBPacket;
import net.questcraft.platform.handler.cscapi.serializer.byteserializer.ByteDeserializationHandler;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.*;

public abstract class CommunicationHandler {
    private final List<SocketPipeline> pipelines;
    private final Set<Class<?>> registeredClasses;

    public CommunicationHandler() {
        this.pipelines = new ArrayList<SocketPipeline>();
        this.registeredClasses = new HashSet<Class<?>>();
    }

    public void registerPipeline(SocketPipeline pipeline) {
        this.pipelines.add(pipeline);
    }

    public void registerPacket(Class<?> cls) {
        this.registeredClasses.add(cls);
    }

    public void onMessage(Session session, byte[] bytes) throws IOException {
        DeserializationHandler serializationHandler = new ByteDeserializationHandler();
        try {
            WBPacket wbPacket =serializationHandler.deserialize(bytes, this.registeredClasses);
        } catch (net.questcraft.platform.handler.cscapi.error.CSCException e) {
            e.printStackTrace();
        }
    }







}
