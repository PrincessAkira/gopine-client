package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;

public class EventModuleDisable extends Event {

    public Module module;

    public EventModuleDisable(Module module) {
        this.module = module;
    }

}