package net.gopine.modules;

public enum ModuleCategory {

    CHAT("CHAT"), GUI("GUI"), RENDERING("RENDERING");

    String category;

    ModuleCategory(String category) {
        this.category = category;
    }

}