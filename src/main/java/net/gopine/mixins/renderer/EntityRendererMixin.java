package net.gopine.mixins.renderer;

import net.gopine.events.impl.client.EventRender;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    /**
     * Calls the render event for module rendering
     * @param p_181560_1_ unsure
     * @param p_181560_2_ unsure
     * @param ci unused
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    @Inject(method = "updateCameraAndRender", at = @At("RETURN"))
    public void updateCameraAndRender(float p_181560_1_, long p_181560_2_, CallbackInfo ci) {
        new EventRender().call();
    }

}