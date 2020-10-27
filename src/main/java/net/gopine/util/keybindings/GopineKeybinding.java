package net.gopine.util.keybindings;

import net.gopine.events.EventSubscriber;

public abstract class GopineKeybinding {

    public String name;
    public int keyCode;

    public GopineKeybinding(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public void onClick() {

    }

}