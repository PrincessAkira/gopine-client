package net.gopine.util;

import net.gopine.assets.gui.GuiGopineHUDEditor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class Utils {

    /**
     * Javadoc unused
     * @param discordRPC instance of {@link GopineRPC}
     * @param currentScreen current guiscreen
     */
    public void checkForDiscordRPCUpdateAvailability(GopineRPC discordRPC, GuiScreen currentScreen) {
        Minecraft mc = Minecraft.getMinecraft();
        if(currentScreen instanceof GuiMainMenu)
            discordRPC.setPresence("Main Menu", "In the menus", "gopinelarge");
            //GopineRPC.
        else if(currentScreen instanceof GuiMultiplayer)
            discordRPC.setPresence("Multiplayer Menu", "In the menus", "gopinelarge");
        else if(currentScreen instanceof GuiOptions)
            discordRPC.setPresence("Options Menu", "In the menus", "gopinelarge");
        else if(currentScreen instanceof GuiVideoSettings)
            discordRPC.setPresence("Video Settings Menu", "In the menus", "gopinelarge");
        else if(currentScreen instanceof GuiLanguage)
            discordRPC.setPresence("Language Settings Menu", "In the menus", "gopinelarge");
        else if(currentScreen instanceof GuiSnooper)
            discordRPC.setPresence("Snooper Menu", "In the menus", "gopinelarge");
        else if(currentScreen instanceof GuiControls)
            discordRPC.setPresence("Controls Menu", "In the menus", "gopinelarge");
        else if(mc.isIntegratedServerRunning())
            discordRPC.setPresence("Playing Singleplayer", "Ingame", "gopinelarge");
        else if(!mc.isIntegratedServerRunning() && mc.getCurrentServerData() != null)
            discordRPC.setPresence("Playing on " + mc.getCurrentServerData().serverIP, "Ingame", "gopinelarge");

    }
    /**
     * Javadoc unused
     */
    public void openModuleHUDConfig() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiGopineHUDEditor());
    }

}