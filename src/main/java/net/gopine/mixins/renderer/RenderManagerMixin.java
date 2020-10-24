package net.gopine.mixins.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(RenderManager.class)
public class RenderManagerMixin {

    @Shadow
    private Map<String, RenderPlayer> skinMap = Maps.<String, RenderPlayer>newHashMap();
    public Map<String, RenderPlayer> getSkinMap() {
        return skinMap;
    }
}