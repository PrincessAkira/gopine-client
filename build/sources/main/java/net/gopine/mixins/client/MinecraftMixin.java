package net.gopine.mixins.client;

import ga.yukii.RessourceLoader;
import net.gopine.GopineClient;
import net.gopine.events.impl.client.EventTick;
import net.gopine.events.impl.gui.EventGuiSwitch;
import net.gopine.events.impl.player.input.EventKeyboardKeyPressed;
import net.gopine.events.impl.player.input.EventKeyboardKeyReleased;
import net.gopine.events.impl.player.input.EventMouseLeftClick;
import net.gopine.events.impl.player.input.EventMouseRightClick;
import net.gopine.events.impl.world.EventWorldJoin;
import net.gopine.modules.impl.togglesprint.GopineSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.WorldSettings;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;



/**
 * Minecraft mixin class used to add client initialization features and events.
 * @author MatthewTGM | MatthewTGM#4058
 * @since b0.1
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Final private DefaultResourcePack mcDefaultResourcePack;

    @Shadow public EntityPlayerSP thePlayer;

    @Shadow public GameSettings gameSettings;

    /**
     * Calls the {@link GopineClient} preInit method on game start.
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */

    @Inject(method = "startGame", at = @At("HEAD"))
    private void preInit(CallbackInfo ci) {
        GopineClient.getInstance().preInit();
    }


    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        Arrays.stream(aint).map(i -> i << 8 | i >> 24 & 255).forEach(bytebuffer::putInt);
        bytebuffer.flip();
        return bytebuffer;
    }

    /**
     * Edits the Icon
     *
     * @param callbackInfo is unused
     * @author Yukii | Azariel#1337
     * @since b0.1
     */

    @Inject(method = "setWindowIcon", at = @At("RETURN"))
    public void setWindowIcon(CallbackInfo callbackInfo) {

        Util.EnumOS util$enumos = Util.getOSType();

        if (util$enumos != Util.EnumOS.OSX)
        {
            InputStream inputstream = null;
            InputStream inputstream1 = null;

            try
            {
                inputstream = new RessourceLoader("icons/rsz_logocircle.png").newStream();
                inputstream1 = new RessourceLoader("icons/rsz_logocircle.png").newStream();

                if(inputstream == null) {
                    throw new Error();
                }

                Display.setIcon(new ByteBuffer[] {this.readImageToBuffer(inputstream), this.readImageToBuffer(inputstream1)});
            }
            catch (IOException ignored)
            {
            }
            finally
            {
                IOUtils.closeQuietly(inputstream);
                IOUtils.closeQuietly(inputstream1);
            }
        }
    }

    /**
     * Calls the {@link GopineClient} init method on game start.
     * @param ci unused
     * @author MatthewTGM
     * @since b0.1
     */
    @Inject(method = "startGame", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        GopineClient.getInstance().init();
    }

    /**
     * Calls the {@link GopineClient} shutdown method on game close.
     * @param ci unused
     * @author MatthewTGM
     * @since b0.1
     */
    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    private void shutdown(CallbackInfo ci) {
        GopineClient.getInstance().shutdown();
    }

    /**
     * Sets all dispay settings.
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    @Inject(method = "createDisplay", at = @At("RETURN"))
    private void createDisplay(CallbackInfo ci) {
        Display.setTitle(GopineClient.CLIENT_NAME + " " + GopineClient.CLIENT_VER + " [" + GopineClient.BRANCH_NAME + "]");
    }

    /*
     * The events of this file begin here.
     */

    /**
     * Calls the following events:
     * {@link EventTick}
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    @Inject(method = "runTick", at = @At("RETURN"))
    public void runTick(CallbackInfo ci) {
        new EventTick().call();
    }

    /**
     * Calls the following events:
     * {@link EventKeyboardKeyPressed}
     * {@link EventKeyboardKeyReleased}
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    @Inject(method = "dispatchKeypresses", at = @At(value = "INVOKE_ASSIGN", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private void runTickKeyboard(CallbackInfo ci) {
        if (Keyboard.getEventKeyState())
            new EventKeyboardKeyPressed(Keyboard.isRepeatEvent(), Keyboard.getEventKey()).call();
        else
            new EventKeyboardKeyReleased(Keyboard.isRepeatEvent(), Keyboard.getEventKey()).call();
    }

    /**
     * Calls the following events:
     * {@link EventMouseLeftClick}
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    @Inject(method = "clickMouse", at = @At("RETURN"))
    private void leftClickMouseButton(CallbackInfo ci) {
        new EventMouseLeftClick().call();
    }

    /**
     * Calls the following events:
     * {@link EventMouseRightClick}
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    private void rightClickMouseButton(CallbackInfo ci) {
        new EventMouseRightClick().call();
    }

    /**
     * Calls the following events:
     * {@link EventGuiSwitch}
     * @param guiScreenIn the gui being called
     * @param ci unused
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo ci) {
        new EventGuiSwitch(guiScreenIn).call();
    }
    
    /**
     * Calls the following events:
     * {@link EventWorldJoin}
     * @param folderName name of the world folder
     * @param worldName name of the world
     * @param worldSettingsIn unused/not needed yet
     * @param callbackInfo unused
     * @author Nebula | Nebula#9998
     * @since b0.1
     */
    @Inject(method = "launchIntegratedServer", at = @At("HEAD"))
    private void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo callbackInfo) {
        new EventWorldJoin(folderName, worldName).call();
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("RETURN"))
    public void loadWorld(WorldClient worldClientIn, String loadingMessage, CallbackInfo callbackInfo) {
        if(Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.movementInput = new GopineSprint();
        }
    }

    @Inject(method = "setDimensionAndSpawnPlayer", at = @At("RETURN"))
    public void setDimensionAndSpawnPlayer(int dimension, CallbackInfo callbackInfo) {
        if(Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.movementInput = new GopineSprint();
        }
    }

}
