package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class WaterOverlaySetting extends Setting {

    public static boolean shouldRender;
    public static void setShouldRender(boolean shouldRender) {
        WaterOverlaySetting.shouldRender = shouldRender;
    }

    public WaterOverlaySetting(boolean toggled) {
        super("Water Overlay", SettingsType.PERFORMANCE, toggled);
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