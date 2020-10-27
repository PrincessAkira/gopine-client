package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class RenderOwnNameSetting extends Setting {

    public static boolean shouldRender;
    public static void setShouldRender(boolean shouldRender) {
        RenderOwnNameSetting.shouldRender = shouldRender;
    }

    public RenderOwnNameSetting(boolean toggled) {
        super("Render own name", SettingsType.RENDERING, toggled);
    }

    @Override
    public void onSettingEnable() {
        setShouldRender(true);
        super.onSettingEnable();
    }

    @Override
    public void onSettingDisable() {
        setShouldRender(false);
        super.onSettingDisable();
    }

}