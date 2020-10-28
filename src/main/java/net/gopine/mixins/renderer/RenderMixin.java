package net.gopine.mixins.renderer;

import net.gopine.settings.impl.RenderOwnNameSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public abstract class RenderMixin<T extends Entity> {

    @Shadow protected abstract void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance);

    /**
     * Renders the players own name depending on the {@link RenderOwnNameSetting} setting
     * @param entity entity
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @param entityYaw entity yaw
     * @param partialTicks partialTicks
     * @param ci unused
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    @Inject(method = "doRender", at = @At("HEAD"))
    protected void renderName(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if(entity instanceof EntityPlayerSP && RenderOwnNameSetting.shouldRender && !entity.isInvisible() && !((EntityPlayerSP) entity).isSpectator()) {
            this.renderLivingLabel(entity, Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText(), x, y, z, 64);
        }
    }

}