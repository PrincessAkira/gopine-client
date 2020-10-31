package net.gopine.mixins.renderer;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.entities.EventEntityJoin;
import net.gopine.events.manager.EventManager;
import net.gopine.https.HttpsGet;
import net.gopine.settings.impl.RenderOwnNameSetting;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Render.class)
public abstract class RenderMixin<T extends Entity> {

    @Shadow protected abstract void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance);

    @Shadow public abstract FontRenderer getFontRendererFromRenderManager();

    private boolean shouldRenderIcon;

    public RenderMixin() {
        EventManager.register(this);
    }

    /**
     * Renders the players own name depending on the {@link RenderOwnNameSetting} setting
     * @param entity entity
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @param entityYaw entity yaw
     * @param partialTicks partialTicks
     * @param ci unused
     * @author MatthewTGM
     * @since b1.0
     */
    @Inject(method = "doRender", at = @At("HEAD"))
    protected void renderName(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        //try {
            //final JSONArray obj = (JSONArray) new JSONParser().parse(new HttpsGet("http://localhost:2185/users").sendGet());
            //obj.forEach(a -> {
                //Logger.CustomLogger.info("Icon Renderer", a);
                //if(entity.getDisplayName().getUnformattedText() == value) {
                //    this.shouldRenderIcon = true;
                //}
            //});
            //if(entity instanceof EntityPlayer && !((EntityPlayer)entity).isSpectator()) {
            //    this.renderCustomStaticLogo(new ResourceLocation("gui/guimainmenu/logoCircle.png"), x, y, z);
            //}
        //} catch(Exception e) {
        //    e.printStackTrace();
        //}

        if(entity instanceof EntityPlayerSP && RenderOwnNameSetting.shouldRender && !entity.isInvisible() && !((EntityPlayerSP) entity).isSpectator()) {
            this.renderLivingLabel(entity, Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText(), x, y, z, 64);
            //this.renderCustomStaticLogo(new ResourceLocation("gui/guimainmenu/logoCircle.png"), x, y, z);
        }
    }

    //TODO: Fix all icon rendering

    /**
     * Used to draw and render the custom logo next to nametags.
     * @author MatthewTGM
     * @since b1.0
     */
    private void renderCustomStaticLogo(ResourceLocation resourceLocation, double x, double y, double z) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawScaledCustomSizeModalRect((int) Math.round(x), (int) Math.round(y), 0, 0, 1, 1, 1, 1, 1, 1);
    }

}