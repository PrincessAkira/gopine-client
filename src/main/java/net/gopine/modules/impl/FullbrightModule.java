package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.minecraft.client.Minecraft;

public class FullbrightModule extends Module {

    public FullbrightModule(boolean toggled) {
        super("Fullbright", ModuleCategory.RENDERING, toggled);
    }

    private float oldGamma;
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onModuleEnable() {
        //if(mc.gameSettings.gammaSetting == 0) return;
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100f;
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        //if(mc.gameSettings.gammaSetting == 0) return;
        mc.gameSettings.gammaSetting = oldGamma;
        super.onModuleDisable();
    }

}