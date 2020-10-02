package net.questcraft.platform.handler.cscapi;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;
import net.questcraft.platform.handler.cscapi.communication.ChannelType;
import net.questcraft.platform.handler.cscapi.error.CSCException;
import net.questcraft.platform.handler.cscapi.error.CSCInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CSCAPI {
    private final Map<ChannelType, ChannelHandler> channelHandlers;
    private static CSCAPI api;

    private CSCAPI() {
        this.channelHandlers = new HashMap<>();
    }

    public ChannelHandler getChannelHandler(ChannelType type) throws CSCException {
        try {
            if (!this.channelHandlers.containsKey(type)) this.channelHandlers.put(type, this.createFromType(type));
            return this.channelHandlers.get(type);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new CSCInstantiationException("Unable to instantiate requested ChannelHandler Type, Error : " + e.getMessage());
        }
    }

    private ChannelHandler createFromType(ChannelType type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends ChannelHandler> clsType = type.getClsType();
        Constructor<? extends ChannelHandler> channelConstructor = clsType.getConstructor();

        ChannelHandler handler = channelConstructor.newInstance();
        return handler;
    }

    public static synchronized CSCAPI getAPI() {
        if (api == null) api = new CSCAPI();
        return api;
    }

}
