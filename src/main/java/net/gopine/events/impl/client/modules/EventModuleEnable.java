package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;

public class EventModuleEnable extends Event {

    public Module module;

    public EventModuleEnable(Module module) {
        this.module = module;
    }

}