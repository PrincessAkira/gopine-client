package net.gopine.modules;

import net.gopine.events.impl.client.modules.EventModuleDisable;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.client.modules.EventModuleToggle;
import net.gopine.util.Logger;

public abstract class Module {

    public String name;
    public ModuleCategory category;
    public boolean toggled;
    public boolean hud;

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

    public Module(String name, ModuleCategory category, boolean toggled, boolean hud) {
        this.name = name;
        this.category = category;
        this.toggled = toggled;
        this.hud = hud;
        this.setupModule();
    }

    public Module(String name, ModuleCategory category, boolean hud) {
        this.name = name;
        this.category = category;
        this.toggled = toggled;
        this.hud = hud;
        this.setupModule();
    }

    /**
     * Sets up the initial module settings etc
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    private void setupModule() {
        Logger.info("Initializing module: " + this.name);
        try {
            this.onModuleSetup();
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