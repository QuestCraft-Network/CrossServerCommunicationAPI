package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.CommunicationHandler;

@FunctionalInterface
public interface ChannelType<T extends CommunicationHandler> {
    /**
     * @return Class<? extends ChannelHandler> The class corresponding to that channel type
     */
    Class<T> getClsType();
}
