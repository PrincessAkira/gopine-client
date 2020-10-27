package net.gopine.modules.impl;

import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.minecraft.client.Minecraft;

public class PingDisplayModule extends RenderedModule {

    public PingDisplayModule(boolean toggled) {
        super("Ping", ModuleCategory.RENDERING, toggled);
    }

    @Override
    public void onRender(ScreenPos pos) {
        font.drawString("[" + Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + " PING]", pos.getExactPosX(), pos.getExactPosY(), -1);
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        font.drawString("[0000 PING]", pos.getExactPosX(), pos.getExactPosY(), -1);
        super.onDummyRender(pos);
    }

}