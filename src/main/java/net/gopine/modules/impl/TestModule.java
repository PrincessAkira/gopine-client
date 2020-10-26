package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.DraggableElement;
import net.gopine.modules.draggable.ScreenPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class TestModule extends Module {

    protected float zLevelFloat;

    public TestModule(boolean toggled) {
        super("Test", ModuleCategory.CHAT, toggled, true);
        this.renderOutline = true;
        this.approximateHeight = 10;
    }

    @Override
    public void onRender(ScreenPos pos) {
        font.drawString("Test Module", pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth("Test Module");
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        font.drawString("Test Module [DUMMY]", pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth("Test Module [DUMMY]");
        super.onDummyRender(pos);
    }

}