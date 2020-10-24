package net.gopine.settings;

import net.gopine.events.impl.client.settings.EventSettingDisable;
import net.gopine.events.impl.client.settings.EventSettingEnable;
import net.gopine.util.Logger;

/**
 * Class used to make settings.
 * @author MatthewTGM | MatthewTGM#4058
 * @since b1.0
 */
public abstract class Setting {

    public final String name;
    public final SettingsType type;
    public boolean toggled;
    /**
     * @return the toggled variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public boolean isToggled() {
        return toggled;
    }
    /**
     * Sets the toggled variable to the parameter provided
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public Setting(String name, SettingsType type, boolean toggled) {
        this.name = name;
        this.type = type;
        this.toggled = toggled;

        if(this.toggled) {
            this.onSettingEnable();
        } else {
            this.onSettingDisable();
        }
        this.setupSetting();
    }

    /**
     * Sets up the initial setting
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void setupSetting() {
        Logger.info("Initializing setting: " + this.name);
        try {
            this.onSettingSetup();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called on setting setup
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onSettingSetup() {

    }

    /**
     * Called on setting enable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onSettingEnable() {
        toggled = true;
        new EventSettingEnable(this).call();
    }

    /**
     * Called on setting disable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onSettingDisable() {
        toggled = false;
        new EventSettingDisable(this).call();
    }

}