package net.gopine;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.gui.EventGuiSwitch;
import net.gopine.events.manager.EventManager;
import net.gopine.modules.ModuleManager;
import net.gopine.modules.impl.TestModule;
import net.gopine.settings.SettingManager;
import net.gopine.util.Logger;
import net.gopine.util.GopineRPC;
import net.gopine.util.Utils;

/**
 * The main class of the client. Where all initialization takes place.
 * @author MatthewTGM | MatthewTGM#4058
 * @since b0.1
 */
public class GopineClient {

    private static final GopineClient INSTANCE = new GopineClient();
    /**
     * @return an instance of GopineClient
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public static GopineClient getInstance() {
        return INSTANCE;
    }
    private static final SettingManager settingManager = new SettingManager();

    public static final String CLIENT_NAME = "Gopine Client", CLIENT_VER = "b0.1", BRANCH_NAME = "BETA";

    private final GopineRPC GOPINE_RPC = new GopineRPC();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    //public static final GopineFontRenderer FONT_RENDERER = new GopineFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 15), 0);
    /**
     * @return an instance of {@link GopineRPC}
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public final GopineRPC getDiscordRPC() { return GOPINE_RPC; }
    /**
     * @return an instance of {@link ModuleManager}
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public final ModuleManager getModuleManager() { return MODULE_MANAGER; };
    /**
     * @return an instance of {@link GopineFontRenderer}
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    //public static GopineFontRenderer getFontRenderer() {
    //return FONT_RENDERER;
    //}

    /**
     * The client preInitialization method.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public void preInit() {
        Logger.info("Started Gopine Client PRE_INIT phase");
        EventManager.register(this);
        this.getDiscordRPC().init();
        Logger.info("Finished Gopine Client PRE_INIT phase");
    }

    /**
     * The client initialization method.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public void init() {
        Logger.info("Started Gopine Client INIT phase");
        MODULE_MANAGER.initModules();
        settingManager.handleSetting(this);
        Logger.info("Finished Gopine Client INIT phase");
    }

    /**
     * The client shutdown method.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public void shutdown() {

    }

    @EventSubscriber
    public void onGuiSwitch(EventGuiSwitch e) {
        try {
            new Utils().checkForDiscordRPCUpdateAvailability(this.getDiscordRPC(), e.screen);
        } catch(Exception ignored) {

        }
    }

    @EventSubscriber
    public void onModuleStart(EventModuleEnable e) {
        Logger.info("Started " + e.module.name);
        if(e.module instanceof TestModule) {
            Logger.info(e.module.name + " was enabled!");
        }
    }

}