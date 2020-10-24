package net.gopine.settings.impl;

import net.gopine.settings.Setting;
import net.gopine.settings.SettingsType;

public class SettingTextShadow extends Setting {

    public static boolean textShadow;
    public static void setTextShadow(boolean textShadow) {
        SettingTextShadow.textShadow = textShadow;
    }

    public SettingTextShadow(boolean toggled) {
        super("Text Shadow", SettingsType.GUI, toggled);
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