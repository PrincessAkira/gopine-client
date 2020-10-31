package net.gopine.mixins.gui;

import net.gopine.assets.gui.GopineButtonRound;
import net.gopine.modules.impl.SpotifyDisplayModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * GuiMainMenu mixin used to completely rewrite the main menu GUI of the game.
 * @author MatthewTGM | MatthewTGM#4058
 * @since b1.0
 */
@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin extends GuiScreen {

    /** OpenGL graphics card warning. */
    private String openGLWarning1;
    /** OpenGL graphics card warning. */
    private String openGLWarning2;
    /** Link to the Mojang Support about minimum requirements */
    private String openGLWarningLink;

    /** The Object object utilized as a thread lock when performing non thread-safe operations */
    private final Object threadLock = new Object();

    private static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private int initalTime;

    /**
     * Fixed the {@link GuiMainMenu} initGui method.
     * @author MatthewTGM
     * @since b1.0
     */
    @Overwrite
    public void initGui() {
        initalTime = (int) Minecraft.getSystemTime();
        this.openGLWarning2 = field_96138_a;
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }

        this.addAllButtons(this.height / 4 + 48, 24);

        synchronized (this.threadLock)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    /**
     * Adds all custom buttons to the GUI.
     * @param p_73969_1_ unsure
     * @param p_73969_2_ unsure
     * @author MatthewTGM
     * @since b1.0
     */
    public void addAllButtons(int p_73969_1_, int p_73969_2_) {
        int j = this.height / 4 + 48;
        this.buttonList.add(new GopineButtonRound(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer"), 6, new Color(255, 255, 255, 133), new Color(199, 226, 84, 133), new Color(0, 100, 50), new Color(0, 100, 50)));
        this.buttonList.add(new GopineButtonRound(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer"), 6, new Color(255, 255, 255, 133), new Color(199, 226, 84, 133), new Color(0, 100, 50), new Color(0, 100, 50)));
        this.buttonList.add(new GopineButtonRound(0, this.width / 2 - 100, p_73969_1_ + p_73969_2_ + 24, I18n.format("menu.options"), 6, new Color(255, 255, 255, 133), new Color(199, 226, 84, 133), new Color(0, 100, 50), new Color(0, 100, 50)));
        this.buttonList.add(new GopineButtonRound(4, this.width / 2 - 100, p_73969_1_ + p_73969_2_ + 48, I18n.format("menu.quit"), 6, new Color(255, 255, 255, 133), new Color(199, 226, 84, 133), new Color(0, 100, 50), new Color(0, 100, 50)));
    }

    /**
     * Used to draw and render the custom background on the main menu.
     * @author MatthewTGM
     * @since b1.0
     */
    private void renderCustomStaticBackground(ResourceLocation resourceLocation, float pt) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
    }

    /**
     * Used to draw and render the custom logo on the main menu.
     * @author Basilicous | Basilicous#9999
     * @since b1.0
     */
    private void renderCustomStaticLogo(ResourceLocation resourceLocation) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        Color borderColor = new Color(255, 255, 255);
        GlStateManager.color(borderColor.getRed() / 255, borderColor.getGreen() / 255, borderColor.getBlue() / 255);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 36, this.height / 4 - 36, 0, 0, 68, 68, 68, 68);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 32, this.height / 4 - 32, 0, 0, 64, 64, 64, 64);
    }

    /**
     * Overwrites the drawScreen method in the GuiMainMenu class. This is used to render custom Strings, images, etc.
     * @param mouseX the X position of the mouse
     * @param mouseY the Y position of the mouse0
     * @param partialTicks the partialTicks
     * @author MatthewTGM
     * @since b1.0
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderCustomStaticBackground(new ResourceLocation("gui/guimainmenu/background.png"), partialTicks);
        GlStateManager.enableAlpha();
        this.renderCustomStaticLogo(new ResourceLocation("gui/guimainmenu/logoCircle.png"));
        int i = 274;

        String clientNameAndVer = "Gopine Client 1.8.9";
        this.drawString(this.fontRendererObj, clientNameAndVer, 2, this.height - 10, 0xFFFFFFFF);
        //GopineClient.getFontRenderer().drawString(clientNameAndVer, 2, this.height - 10, 0xFFFFFFFF);
        String copyright = "Copyright Mojang AB. Do not distribute!";
        String notAffiliated = "Gopine Client is not affiliated with Mojang AB";
        this.drawString(this.fontRendererObj, copyright, this.width - this.fontRendererObj.getStringWidth(copyright) - 2, this.height - 10, -1);
        this.drawString(this.fontRendererObj, notAffiliated, this.width - this.fontRendererObj.getStringWidth(notAffiliated) - 2, this.height - 20, -1);

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}