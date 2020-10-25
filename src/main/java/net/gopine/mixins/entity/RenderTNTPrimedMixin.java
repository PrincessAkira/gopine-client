package net.gopine.mixins.entity;

import net.gopine.modules.impl.TNTTimerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.text.DecimalFormat;

@Mixin(RenderTNTPrimed.class)
public class RenderTNTPrimedMixin {

    private Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "doRender", at = @At("HEAD"))
    public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if(TNTTimerModule.shouldRenderTag) {
            try {
                this.renderTag(RenderTNTPrimed.class.cast(Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityTNTPrimed.class)), entity, x, y, z, partialTicks);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void renderTag(final RenderTNTPrimed tntRenderer, final EntityTNTPrimed tntPrimed, final double x, final double y, final double z, final float partialTicks) {
        if (tntPrimed.fuse < 1) {
            return;
        }
        final double d0 = tntPrimed.getDistanceSqToEntity(tntRenderer.getRenderManager().livingPlayer);
        if (d0 <= 4096.0) {
            final float number = (tntPrimed.fuse - partialTicks) / 20.0f;
            final String str = new DecimalFormat("0.00").format(number);
            final FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
            final float f = 1.6f;
            final float f2 = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + 0.0f, (float)y + tntPrimed.height + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            int xMultiplier = 1;
            if (this.mc != null && this.mc.gameSettings != null && this.mc.gameSettings.thirdPersonView == 2) {
                xMultiplier = -1;
            }
            GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * (float)xMultiplier, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-f2, -f2, f2);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final int i = 0;
            final int j = fontrenderer.getStringWidth(str) / 2;
            final float green = Math.min((float)tntPrimed.fuse / 80.0f, 1.0f);
            final Color color = new Color(1.0f - green, green, 0.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, color.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
}