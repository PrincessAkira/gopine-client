package net.gopine.settings;

public enum SettingsType {

    CHAT("CHAT"),
    GUI("GUI"),
    PERFORMANCE("PERFORMANCE");

    public String type;

    SettingsType(String type) {
        this.type = type;
    }

}