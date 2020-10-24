package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class TextShadowSetting extends Setting {

    public static boolean textShadow;
    public static void setTextShadow(boolean textShadow) {
        TextShadowSetting.textShadow = textShadow;
    }

    public TextShadowSetting(boolean toggled) {
        super("Text Shadow", SettingsType.PERFORMANCE, toggled);
    }

    @Override
    public void onSettingEnable() {
        setTextShadow(false);
        super.onSettingEnable();
    }

    @Override
    public void onSettingDisable() {
        setTextShadow(true);
        super.onSettingDisable();
    }
}