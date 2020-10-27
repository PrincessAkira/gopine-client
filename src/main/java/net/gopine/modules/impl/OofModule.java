package net.gopine.modules.impl;

import net.gopine.events.manager.EventManager;
import net.gopine.listeners.OofModListener;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class OofModule extends Module {

    public static boolean shouldPlay;
    public static void setShouldPlay(boolean shouldPlay) {
        OofModule.shouldPlay = shouldPlay;
    }

    private OofModListener listener = new OofModListener();

    public static float volume = 30.0f;
    public static float getVolume() {
        return volume;
    }

    public OofModule(boolean toggled) {
        super("OofMod", ModuleCategory.GAMEPLAY, toggled);
    }

    @Override
    public void onModuleEnable() {
        setShouldPlay(true);
        EventManager.register(listener);
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        setShouldPlay(false);
        EventManager.unregister(listener);
        super.onModuleDisable();
    }

}