package net.gopine.modules;

import net.gopine.GopineClient;
import net.gopine.events.impl.client.modules.EventModuleDisable;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.client.modules.EventModuleToggle;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;

public abstract class Module {

    public String name;
    public ModuleCategory category;
    public boolean toggled;

    protected final Minecraft mc = Minecraft.getMinecraft();

    /**
     * @return an instance of the module toggled variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public boolean isToggled() {
        return toggled;
    }
    /**
     * @param toggled the state of the module toggle
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public Module(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;
        if(GopineClient.getInstance().getFileHandler().readModuleData(this.name) == null) {
            this.toggled = true;
        } else {
            toggled = (boolean) GopineClient.getInstance().getFileHandler().readModuleData(this.name).get("toggle_state");
        }
        this.setupModule();
    }

    /**
     * Sets up the initial module settings etc
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    private void setupModule() {
        Logger.ModLogger.info("Initializing module: " + this.name);
        try {
            this.onModuleSetup();
            this.saveModuleData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called on module setup
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleSetup() {

    }

    /**
     * Saves basic module data (NAME, CATEGORY, TOGGLE STATE)
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    public void saveModuleData() {
        GopineClient.getInstance().getFileHandler().saveBasicModuleData(this.name, this.category, this.toggled);
    }

    /**
     * Called on module enable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleEnable() {
        new EventModuleEnable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

    /**
     * Called on module disable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleDisable() {
        new EventModuleDisable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

}