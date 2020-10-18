package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;

public class EventModuleToggle extends Event {

    public Module module;
    public boolean state;

    public EventModuleToggle(Module module, boolean state) {
        this.module = module;
        this.state = state;
    }

}