package net.gopine.mixins.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

  @Shadow
  private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
  private Minecraft mc;


  /**
   * Disables the Underwater Overlay
   * @author Yukii | Azariel#0004
   * @since b0.1
   */


  @Inject(method = "renderWaterOverlayTexture", at = @At("RETURN"))
  public void renderWaterOverlayTexture(float p_78448_1_, CallbackInfo ci) {

    //Fuck you Notch for you wateroverlay cancer shit

    /*
    this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    float f = this.mc.thePlayer.getBrightness(p_78448_1_);
    GlStateManager.color(f, f, f, 0F);
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.pushMatrix();
    float f1 = 4.0F;
    float f2 = -1.0F;
    float f3 = 1.0F;
    float f4 = -1.0F;
    float f5 = 1.0F;
    float f6 = -0.5F;
    float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
    float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
    worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((double)(4.0F + f7), (double)(4.0F + f8)).endVertex();
    worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((double)(0.0F + f7), (double)(4.0F + f8)).endVertex();
    worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((double)(0.0F + f7), (double)(0.0F + f8)).endVertex();
    worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((double)(4.0F + f7), (double)(0.0F + f8)).endVertex();
    tessellator.draw();
    GlStateManager.popMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.disableBlend(); */
  }

  /**
   * Disables the rendering of the Fire Overlay
   * @author Yukii | Azariel#0004
   * @since b0.1
   */

  @Inject(method = "renderFireInFirstPerson", at = @At("RETURN"))
  public void renderFireInFirstPerson(float p_78442_1_, CallbackInfo ci) {

    // bye bye fire you retard

/*
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 0F);
    GlStateManager.depthFunc(519);
    GlStateManager.depthMask(false);
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    float f = 1.0F;

    for (int i = 0; i < 2; ++i) {
      GlStateManager.pushMatrix();
      TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      float f1 = textureatlassprite.getMinU();
      float f2 = textureatlassprite.getMaxU();
      float f3 = textureatlassprite.getMinV();
      float f4 = textureatlassprite.getMaxV();
      float f5 = (0.0F - f) / 2.0F;
      float f6 = f5 + f;
      float f7 = 0.0F - f / 2.0F;
      float f8 = f7 + f;
      float f9 = -0.5F;
      GlStateManager.translate((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
      GlStateManager.rotate((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double) f5, (double) f7, (double) f9).tex((double) f2, (double) f4).endVertex();
      worldrenderer.pos((double) f6, (double) f7, (double) f9).tex((double) f1, (double) f4).endVertex();
      worldrenderer.pos((double) f6, (double) f8, (double) f9).tex((double) f1, (double) f3).endVertex();
      worldrenderer.pos((double) f5, (double) f8, (double) f9).tex((double) f2, (double) f3).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
     }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        */
    }
  }
