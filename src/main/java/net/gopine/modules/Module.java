package net.gopine.modules;

import net.gopine.events.impl.client.modules.EventModuleDisable;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.client.modules.EventModuleToggle;

public abstract class Module {

    public String name;
    public ModuleCategory category;
    public boolean toggled;
    public boolean hud;
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public Module(String name, ModuleCategory category, boolean toggled, boolean hud) {
        this.name = name;
        this.category = category;
        this.toggled = toggled;
        this.hud = hud;
    }

    public void onModuleEnable() {
        new EventModuleEnable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

    public void onModuleDisable() {
        new EventModuleDisable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

}