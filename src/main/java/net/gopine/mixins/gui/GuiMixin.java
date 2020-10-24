package net.gopine.mixins.gui;

import net.gopine.settings.impl.SettingTextShadow;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Gui.class)
public class GuiMixin {

    /**
     * Modifies the drawCenteredString method in {@link Gui} to add the {@link SettingTextShadow} setting
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    @Overwrite
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {

        fontRendererIn.drawString(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color, SettingTextShadow.textShadow);

    }

    /**
     * Modifies the drawCenteredString method in {@link Gui} to add the {@link SettingTextShadow} setting
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    @Overwrite
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {

        fontRendererIn.drawString(text, (float)x, (float)y, color, SettingTextShadow.textShadow);

    }

}