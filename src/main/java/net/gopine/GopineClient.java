package net.gopine;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.gui.EventGuiSwitch;
import net.gopine.events.manager.EventManager;
import net.gopine.modules.ModuleManager;
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

    public static final String CLIENT_NAME = "Gopine Client", CLIENT_VER = "b0.1", BRANCH_NAME = "BETA";

    private final GopineRPC GOPINE_RPC = new GopineRPC();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    private final SettingManager SETTING_MANAGER = new SettingManager();
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
     * @return an instance of {@link SettingManager}
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }

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
        SETTING_MANAGER.initSettings();
        Logger.info("Finished Gopine Client INIT phase");
    }

    /**
     * The client shutdown method.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public void shutdown() {

    }

    /**
     * Changes the DiscordRPC fields based on current client actions
     * @param e event variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    @EventSubscriber
    public void onGuiSwitch(EventGuiSwitch e) {
        try {
            new Utils().checkForDiscordRPCUpdateAvailability(this.getDiscordRPC(), e.screen);
        } catch(Exception ignored) {
        }
    }

}