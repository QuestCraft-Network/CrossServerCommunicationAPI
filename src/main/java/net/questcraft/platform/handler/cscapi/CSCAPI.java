package net.questcraft.platform.handler.cscapi;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.ChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CSCAPI {
    private final Map<ChannelType<?>, CommunicationHandler> channelHandlers;
    private static CSCAPI api;

    private CSCAPI() {
        this.channelHandlers = new HashMap<>();
    }


    /**
     * Creates and retrieves the specified ChannelHandler
     *
     * @param type The requested channel handler Type
     * @return The ChannelHandler that was either instantiated or retrieved from the Map
     * @throws CSCException Throws if unable to instantiate the ChannelHandler
     */
    @SuppressWarnings("unchecked")
    public <T extends CommunicationHandler> T getChannelHandler(ChannelType<T> type) throws CSCException {
        try {
            if (!this.channelHandlers.containsKey(type)) this.channelHandlers.put(type, this.createFromType(type));
            return (T) this.channelHandlers.get(type); //Always true since <T extends CommunicationHandler> and Value will be of CommunicationHandler
        } catch (ReflectiveOperationException e) {
            throw new CSCInstantiationException("Unable to instantiate requested ChannelHandler Type, Error : " + e.getMessage());
        }
    }

    /**
     * A private utility to create a ChannelHandler from a ChannelType
     *
     * @param type Channel Type to create
     * @return The Created ChannelHandler
     * @throws NoSuchMethodException Throws if specified constructor isn't found
     * @throws IllegalAccessException throws if the constructor cant create a new Instance
     * @throws InvocationTargetException throws if the constructor cant create a new Instance
     * @throws InstantiationException throws if the constructor cant create a new Instance
     */
    private <T extends CommunicationHandler> T createFromType(ChannelType<T> type) throws ReflectiveOperationException {
        Class<T> clsType = type.getClsType();
        Constructor<T> channelConstructor = clsType.getConstructor();
        return channelConstructor.newInstance();
    }

    /**
     * Returns the singleton CSCAPI
     *
     * @return The singleton CSCAPI
     */
    public static CSCAPI getAPI() {
        synchronized (CSCAPI.class) {
            if (api == null) api = new CSCAPI();
            return api;
        }
    }
}
