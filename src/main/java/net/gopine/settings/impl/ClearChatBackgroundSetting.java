package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class ClearChatBackgroundSetting extends Setting {

    public static boolean shouldRender;
    public static void setShouldRender(boolean shouldRender) {
        ClearChatBackgroundSetting.shouldRender = shouldRender;
    }

    public ClearChatBackgroundSetting(boolean toggled) {
        super("Clear Chat Background", SettingsType.GUI, toggled);
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