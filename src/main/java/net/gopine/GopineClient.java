package net.gopine;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.gui.EventGuiSwitch;
import net.gopine.events.manager.EventManager;
import net.gopine.https.HttpsPost;
import net.gopine.modules.ModuleManager;
import net.gopine.settings.SettingManager;
import net.gopine.util.Logger;
import net.gopine.util.GopineRPC;
import net.gopine.util.SessionChanger;
import net.gopine.util.Utils;
import net.gopine.util.files.FileHandler;
import net.gopine.util.keybindings.GopineKeybinding;
import net.gopine.util.keybindings.KeybindingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.json.simple.JSONObject;
import org.lwjgl.input.Keyboard;

/**
 * The main class of the client. Where all initialization takes place.
 * @author MatthewTGM
 * @since b0.1
 */
public class GopineClient {

    private static final GopineClient INSTANCE = new GopineClient();
    /**
     * @return an instance of GopineClient
     * @author MatthewTGM
     * @since b0.1
     */
    public static GopineClient getInstance() {
        return INSTANCE;
    }

    public static final String CLIENT_NAME = "Gopine Client", CLIENT_VER = "b0.1", BRANCH_NAME = "BETA";

    private final GopineRPC GOPINE_RPC = new GopineRPC();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    private final SettingManager SETTING_MANAGER = new SettingManager();
    private final KeybindingManager KEYBIND_MANAGER = new KeybindingManager();
    private final FileHandler FILE_HANDLER = new FileHandler();
    /**
     * @return an instance of {@link GopineRPC}
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public final GopineRPC getDiscordRPC() { return GOPINE_RPC; }
    /**
     * @return an instance of {@link ModuleManager}
     * @author MatthewTGM
     * @since b1.0
     */
    public final ModuleManager getModuleManager() { return MODULE_MANAGER; };
    /**
     * @return an instance of {@link SettingManager}
     * @author MatthewTGM
     * @since b1.0
     */
    public SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }
    /**
     * @return an instance of {@link KeybindingManager}
     * @author MatthewTGM
     * @since b1.0
     */
    public KeybindingManager getKeybindingsManager() {
        return KEYBIND_MANAGER;
    }
    /**
     * @return an instance of {@link FileHandler}
     * @author MatthewTGM
     * @since b1.0
     */
    public FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    /**
     * The client preInitialization method.
     * @author MatthewTGM
     * @since b0.1
     */
    public void preInit() {
        Logger.info("Started Gopine Client PRE_INIT phase");
        EventManager.register(this);
        this.getDiscordRPC().init();
        FILE_HANDLER.initFiles();
        KEYBIND_MANAGER.initKeybindingManager();
        Logger.info("Finished Gopine Client PRE_INIT phase");
    }

    /**
     * The client initialization method.
     * @author MatthewTGM
     * @since b0.1
     */
    public void init() {
        Logger.info("Started Gopine Client INIT phase");
        KEYBIND_MANAGER.registerKeybinding(new GopineKeybinding("Open HUD Editor", Keyboard.KEY_RSHIFT) {

            @Override
            public void onClick() {
                new Utils().openModuleHUDConfig();
                super.onClick();
            }

        });
        /*KEYBIND_MANAGER.registerKeybinding(new GopineKeybinding("Open TEMPORARY module gui", Keyboard.KEY_B) {

            @Override
            public void onClick() {
                Minecraft.getMinecraft().displayGuiScreen(new GuiModuleToggleTEMP());
                super.onClick();
            }

        });*/
        KEYBIND_MANAGER.registerKeybinding(new GopineKeybinding("Drop Stack", Keyboard.KEY_U) {

            @Override
            public void onClick() {
                final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                player.dropOneItem(true);
                super.onClick();
            }

        });
        MODULE_MANAGER.initModules();
        SETTING_MANAGER.initSettings();
        SessionChanger.getInstance().setUser("matthewtgm120@gmail.com", "EpicGamer20019.");
        //this.postForNametagIcon("LOGIN");
        //this.sentDiscordWebhookPostForPlayerLogin("LOGIN");
        Logger.info("Finished Gopine Client INIT phase");
    }
    /**
     * The client shutdown method.
     * @author MatthewTGM
     * @since b0.1
     */
    public void shutdown() {
        Logger.info("Started Gopine Client SHUTDOWN phase");
        //this.postForNametagIcon("SHUTDOWN");
        //this.sentDiscordWebhookPostForPlayerLogin("SHUTDOWN");
        Logger.info("Finished Gopine Client SHUTDOWN phase");
    }
    /**
     * Sends the post to a "socket" which determines whether the nametag icon should render
     * @param postType the type of post (LOGIN or SHUTDOWN **ONLY**!!!)
     * @author MatthewTGM
     * @since b1.0
     */
    private void postForNametagIcon(String postType) {
        //TODO: Add nametag icon rendering in RenderMixin
        if(Minecraft.getMinecraft().getSession().getUsername() != null) {
            final JSONObject dataObj = new JSONObject();
            dataObj.put("username", Minecraft.getMinecraft().getSession().getUsername());
            dataObj.put("type", postType);
            Logger.CustomLogger.info("Webhook Data", dataObj);
            new HttpsPost("http://localhost:2185/clientsocket", dataObj.toJSONString(), null);
        }
    }
    /*private String moduleData = "";
    private String renderedModuleData = "";
    private void sentDiscordWebhookPostForPlayerLogin(String type) {
        String msg = "";
        try {
            final JSONObject obj = new JSONObject();
            if(type.equals("LOGIN")) {
                msg = Minecraft.getMinecraft().getSession().getUsername() + " logged into Gopine Client\nData is: ";
                this.getModuleManager().getModuleArray().forEach(m -> {
                    this.moduleData += "{\"name\": \"" + m.name + "\", \"toggle_state\": " + m.isToggled() + ", \"category\": \"" + m.category.toString() + "\"\n";
                });
                msg += "```json\n" + moduleData + "```\n";
                this.getModuleManager().getRenderedModuleArray().forEach(rm -> {
                    this.renderedModuleData += "{\"name\": \"" + rm.name + "\", \"toggle_state\": " + rm.isToggled() + ", \"category\": \"" + rm.category.toString() + "\", \"pos\": {\"x\": " + rm.getScreenPos().getExactPosX() + ", \"y\": " + rm.getScreenPos().getExactPosY() + "}}\n";
                });
                msg += "```json\n" + renderedModuleData + "```";
            } else if(type.equals("SHUTDOWN")) {
                msg = Minecraft.getMinecraft().getSession().getUsername() + " disconnected from Gopine Client\nData is: [INSERT DATA]";
            }
            Logger.CustomLogger.info("Discord Webhook POST", msg);
            obj.put("username", Minecraft.getMinecraft().getSession().getUsername());
            obj.put("content", msg);
            new HttpsPost("https://discord.com/api/webhooks/771767270468485200/M9A9KRbfEXDsRUh9CTHhfwtQnyUAz5ubkKdn8KEPf5AgQCArH-pz019Gs38dgyAPDg5Z", obj.toString(), null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
    /**
     * Changes the DiscordRPC fields based on current client actions
     * @param event event variable
     * @author MatthewTGM
     * @since b1.0
     */
    @EventSubscriber
    public void onGuiSwitch(EventGuiSwitch event) {
        try {
            new Utils().checkForDiscordRPCUpdateAvailability(this.getDiscordRPC(), event.screen);
        } catch(Exception ignored) {
        }
    }

}
