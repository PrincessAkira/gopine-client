package net.gopine.settings;

import net.gopine.GopineClient;
import net.gopine.settings.impl.SettingTextShadow;
import net.gopine.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

    private List<Setting> settingsArray = new ArrayList<>();

    public List<Setting> getSettingsArray() {
        return settingsArray;
    }

    public void initSettings() {
        settingsArray.add(new SettingTextShadow(true));
        this.getSettingsArray().forEach(s -> {
            if(s.isToggled()) {
                s.onSettingEnable();
            } else {
                s.onSettingDisable();
            }
        });
        Logger.info("Registered " + this.getSettingCount(false) + " settings | " + this.getSettingCount(true) + " are enabled!");
    }

    public int getSettingCount(boolean toggled) {
        if(toggled) {
            return (int) settingsArray.stream().filter(Setting::isToggled).count();
        } else {
            return settingsArray.size();
        }
    }

}