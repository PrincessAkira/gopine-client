package ga.matthewtgm.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {

    /**
     * Sends a chat message as the player
     * @param message the message being sent
     */
    public void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    /**
     * Adds a message the the players chat
     * @param message the message being added
     */
    public void addChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

}