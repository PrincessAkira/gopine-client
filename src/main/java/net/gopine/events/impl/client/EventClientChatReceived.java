package net.gopine.events.impl.client;

import net.gopine.events.Event;
import net.minecraft.util.IChatComponent;

/**
 * Calls when a chat message is received by the client player
 * @author MatthewTGM
 * @since b1.0
 */
public class EventClientChatReceived extends Event {
    public IChatComponent message;
    public final byte type;

    /** @param type unused
     * @param message chat component being received.
     */
    public EventClientChatReceived(byte type, IChatComponent message)
    {
        this.type = type;
        this.message = message;
    }
}