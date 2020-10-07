package net.questcraft.platform.handler.cscapi.communication.channeltypes;

import net.questcraft.platform.handler.cscapi.communication.ChannelHandler;

public interface ChannelType {
    /**
     * Gets the ID of the ChannelType
     *
     * @return ChannelType ID
     */
    int getId();

    /**
     *
     * @return Class<? extends ChannelHandler> The class corresponding to that channel type
     */
    Class<? extends ChannelHandler> getClsType();
}
