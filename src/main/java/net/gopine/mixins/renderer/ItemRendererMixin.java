package net.gopine.mixins.renderer;

import net.gopine.modules.impl.OverlayToggleModule;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Item renderer mixin class
 *
 * @author Yukii | Azariel#0004 && Basilicous
 * @since 0.1b
 */
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

  @Shadow
  private Minecraft mc;
  @Shadow
  private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

  /**
   * Disables the rendering of the Fire Overlay and the Water overlay
   *
   * @author Yukii | Azariel#0004 && Basilicous
   * @since 0.1b
   */
  @Overwrite
  public void renderOverlays(float partialTicks) {
    GlStateManager.disableAlpha();

    if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
      IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
      EntityPlayer entityplayer = this.mc.thePlayer;

      for (int i = 0; i < 8; ++i) {
        double d0 = entityplayer.posX + (double) (((float) ((i >> 0) % 2) - 0.5F) * entityplayer.width * 0.8F);
        double d1 = entityplayer.posY + (double) (((float) ((i >> 1) % 2) - 0.5F) * 0.1F);
        double d2 = entityplayer.posZ + (double) (((float) ((i >> 2) % 2) - 0.5F) * entityplayer.width * 0.8F);
        BlockPos blockpos = new BlockPos(d0, d1 + (double) entityplayer.getEyeHeight(), d2);
        IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos);

        if (iblockstate1.getBlock().isVisuallyOpaque()) {
          iblockstate = iblockstate1;
        }
      }

      if (iblockstate.getBlock().getRenderType() != -1) {
        this.func_178108_a(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
      }
    }

    // CHANGES ARE HERE
    if (OverlayToggleModule.overlayToggled) {
      if (!this.mc.thePlayer.isSpectator()) {
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
          this.renderWaterOverlayTexture(partialTicks);
        }

        if (this.mc.thePlayer.isBurning()) {
          this.renderFireInFirstPerson(partialTicks);
        }
      }
    }
    GlStateManager.enableAlpha();
  }

  @Shadow
  private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
    this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    float f = 0.1F;
    GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
    GlStateManager.pushMatrix();
    float f1 = -1.0F;
    float f2 = 1.0F;
    float f3 = -1.0F;
    float f4 = 1.0F;
    float f5 = -0.5F;
    float f6 = p_178108_2_.getMinU();
    float f7 = p_178108_2_.getMaxU();
    float f8 = p_178108_2_.getMinV();
    float f9 = p_178108_2_.getMaxV();
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
    worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((double) f7, (double) f9).endVertex();
    worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((double) f6, (double) f9).endVertex();
    worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((double) f6, (double) f8).endVertex();
    worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((double) f7, (double) f8).endVertex();
    tessellator.draw();
    GlStateManager.popMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
  }

  @Shadow
  /**
   * Renders the fire on the screen for first person mode. Arg: partialTickTime
   */
  private void renderFireInFirstPerson(float p_78442_1_)
  {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
    GlStateManager.depthFunc(519);
    GlStateManager.depthMask(false);
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    float f = 1.0F;

    for (int i = 0; i < 2; ++i)
    {
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
      GlStateManager.translate((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
      GlStateManager.rotate((float)(i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)f5, (double)f7, (double)f9).tex((double)f2, (double)f4).endVertex();
      worldrenderer.pos((double)f6, (double)f7, (double)f9).tex((double)f1, (double)f4).endVertex();
      worldrenderer.pos((double)f6, (double)f8, (double)f9).tex((double)f1, (double)f3).endVertex();
      worldrenderer.pos((double)f5, (double)f8, (double)f9).tex((double)f2, (double)f3).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
    }

    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.disableBlend();
    GlStateManager.depthMask(true);
    GlStateManager.depthFunc(515);
  }

  @Shadow
  /**
   * Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound
   * before being called. Used for the water overlay. Args: parialTickTime
   */
  private void renderWaterOverlayTexture(float p_78448_1_)
  {
    this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    float f = this.mc.thePlayer.getBrightness(p_78448_1_);
    GlStateManager.color(f, f, f, 0.5F);
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
    GlStateManager.disableBlend();
  }
}
