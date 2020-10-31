package net.gopine.modules.impl;

import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class DirectionDisplayModule extends RenderedModule {

    public DirectionDisplayModule() {
        super("DirectionHUD", ModuleCategory.RENDERING);
        this.approximateWidth = 65;
        this.approximateHeight = 12;
    }

    protected static float zLevel = -100.0F;

    private final static String markerColorDefault = "c";
    public static String markerColor = markerColorDefault;
    private final static int compassIndexDefault = 0;
    public static int compassIndex = compassIndexDefault;

    @Override
    public void onRender(ScreenPos pos) {
        int direction = MathHelper.floor_double(((mc.thePlayer.rotationYaw * 256F) / 360F) + 0.5D) & 255;
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/modules/directionhud/compass.png"));
        if (direction < 128) {
            drawTexturedModalRect(pos.getExactPosX(), pos.getExactPosY(), direction, (compassIndex * 24), 65, 12);
        } else {
            drawTexturedModalRect(pos.getExactPosX(), pos.getExactPosY(), direction - 128, (compassIndex * 24) + 12, 65, 12);
        }
        mc.fontRendererObj.drawString("\247" + markerColor.toLowerCase() + "|", pos.getExactPosX() + 32, pos.getExactPosY() + 1, 0xffffff);
        mc.fontRendererObj.drawString("\247" + markerColor.toLowerCase() + "|\247r", pos.getExactPosX() + 32, pos.getExactPosY() + 5, 0xffffff);
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        int direction = MathHelper.floor_double(((mc.thePlayer.rotationYaw * 256F) / 360F) + 0.5D) & 255;
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/modules/directionhud/compass.png"));
        if (direction < 128)
            drawTexturedModalRect(pos.getExactPosX(), pos.getExactPosY(), direction, (compassIndex * 24), 65, 12);
        else
            drawTexturedModalRect(pos.getExactPosX(), pos.getExactPosY(), direction - 128, (compassIndex * 24) + 12, 65, 12);
        mc.fontRendererObj.drawString("\247" + markerColor.toLowerCase() + "|", pos.getExactPosX() + 32, pos.getExactPosY() + 1, 0xffffff);
        mc.fontRendererObj.drawString("\247" + markerColor.toLowerCase() + "|\247r", pos.getExactPosX() + 32, pos.getExactPosY() + 5, 0xffffff);
        super.onDummyRender(pos);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }

}