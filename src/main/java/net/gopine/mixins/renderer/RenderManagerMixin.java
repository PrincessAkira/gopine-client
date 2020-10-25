package net.gopine.mixins.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

/**
 * Only used to fetch the skin map values
 * @author MatthewTGM | MatthewTGM#4058
 * @since b0.1
 */
@Mixin(RenderManager.class)
public class RenderManagerMixin {

    @Shadow private Map<String, RenderPlayer> skinMap = Maps.<String, RenderPlayer>newHashMap();
    public Map<String, RenderPlayer> getSkinMap() {
        return skinMap;
    }
}