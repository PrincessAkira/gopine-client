package net.gopine.mixins.client;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

    /**
     * @return Minecrafts client brand
     * @author MatthewTGN | MatthewTGM#4058
     * @since b1.0
     */
    @Overwrite
    public static String getClientModName()
    {
        return "gopine";
    }

}