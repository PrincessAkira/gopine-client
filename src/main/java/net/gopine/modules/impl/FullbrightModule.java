package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.minecraft.client.Minecraft;

public class FullbrightModule extends Module {

    public FullbrightModule(boolean toggled) {
        super("Fullbright", ModuleCategory.RENDERING, toggled, false);
    }

    private float OldSettings;
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onModuleEnable() {
        OldSettings = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100f;
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        mc.gameSettings.gammaSetting = OldSettings;
        super.onModuleDisable();
    }

}