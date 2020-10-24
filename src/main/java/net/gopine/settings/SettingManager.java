package net.gopine.settings;

import net.gopine.settings.impl.FireOverlaySetting;
import net.gopine.settings.impl.TextShadowSetting;
import net.gopine.settings.impl.WaterOverlaySetting;
import net.gopine.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

    private List<Setting> settingsArray = new ArrayList<>();

    /**
     * @return an instance of the settingsArray variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public List<Setting> getSettingsArray() {
        return settingsArray;
    }

    /**
     * Initialized all settings in the client
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void initSettings() {
        settingsArray.add(new TextShadowSetting(true));
        settingsArray.add(new WaterOverlaySetting(true));
        settingsArray.add(new FireOverlaySetting(true));
        this.getSettingsArray().forEach(s -> {
            if(s.isToggled()) {
                s.onSettingEnable();
            } else {
                s.onSettingDisable();
            }
        });
        Logger.info("Registered " + this.getSettingCount(false) + " settings | " + this.getSettingCount(true) + " are enabled!");
    }

    /**
     * @return the count of all modules currently in the client
     * @param toggled whether to return only enabled modules or disabled
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public int getSettingCount(boolean toggled) {
        if(toggled) {
            return (int) settingsArray.stream().filter(Setting::isToggled).count();
        } else {
            return settingsArray.size();
        }
    }

}