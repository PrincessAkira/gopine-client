package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.ScreenPos;
import net.minecraft.client.Minecraft;

public class FPSDisplayModule extends Module {

    public FPSDisplayModule(boolean toggled) {
        super("FPS", ModuleCategory.RENDERING, toggled, true);
        this.approximateHeight = 10;
    }

    @Override
    public void onRender(ScreenPos pos) {
        String s = Minecraft.getDebugFPS() + " FPS";
        font.drawString(s, pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth(s);
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        font.drawString("999 FPS", pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth("999 FPS");
        super.onDummyRender(pos);
    }

}