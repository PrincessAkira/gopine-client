package net.gopine.util.keybindings;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.player.input.EventKeyboardKeyPressed;
import net.gopine.events.manager.EventManager;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class KeybindingManager {

    private Minecraft mc = Minecraft.getMinecraft();

    private List<GopineKeybinding> keybindingList = new ArrayList<>();

    /**
     * Registers a new keybinding into the client
     * @param keybinding the keybinding being registered
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    public void registerKeybinding(GopineKeybinding keybinding) {
        this.keybindingList.add(keybinding);
    }
    /**
     * Initializes the original keybinding manager
     */
    public void initKeybindingManager() {
        EventManager.register(this);
    }

    @EventSubscriber
    public void onKeyPress(EventKeyboardKeyPressed event) {
        keybindingList.forEach(k -> {
            if(mc.currentScreen == null) {
                if(event.keyCode == k.keyCode) {
                    k.onClick();
                }
            }
        });
    }

}