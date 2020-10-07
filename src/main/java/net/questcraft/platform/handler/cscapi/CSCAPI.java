package net.questcraft.platform.handler.cscapi;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.channeltypes.ChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CSCAPI {
    //TODO Redo this, maybe as a singleton interface with some implementation, or a abstract class... Just make this a utilities class for keeping the singleton, and have something else that deals with the actual implementation.

    private final Map<ChannelType, ChannelHandler> channelHandlers;
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
    public ChannelHandler getChannelHandler(ChannelType type) throws CSCException {
        try {
            if (!this.channelHandlers.containsKey(type)) this.channelHandlers.put(type, this.createFromType(type));
            return this.channelHandlers.get(type);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
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
    private ChannelHandler createFromType(ChannelType type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends ChannelHandler> clsType = type.getClsType();
        Constructor<? extends ChannelHandler> channelConstructor = clsType.getConstructor();

        ChannelHandler handler = channelConstructor.newInstance();
        return handler;
    }

    /**
     * Returns the singleton CSAPI
     *
     * @return The singleton CSCAPI
     */
    public static synchronized CSCAPI getAPI() {
        if (api == null) api = new CSCAPI();
        return api;
    }

}
