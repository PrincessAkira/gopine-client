package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class FireOverlaySetting extends Setting {

    public static boolean shouldRender;
    public static void setShouldRender(boolean shouldRender) {
        FireOverlaySetting.shouldRender = shouldRender;
    }

    public FireOverlaySetting(boolean toggled) {
        super("Fire Overlay", SettingsType.PERFORMANCE, toggled);
    }

    @Override
    public void onSettingEnable() {
        setShouldRender(false);
        super.onSettingEnable();
    }

    @Override
    public void onSettingDisable() {
        setShouldRender(true);
        super.onSettingDisable();
    }

}