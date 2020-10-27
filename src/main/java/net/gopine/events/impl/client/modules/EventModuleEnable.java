package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.RenderedModule;

public class EventModuleEnable extends Event {

    public Module module;
    public RenderedModule renderedModule;

    public EventModuleEnable(Module module) {
        this.module = module;
    }

    public EventModuleEnable(RenderedModule renderedModule) {
        this.renderedModule = renderedModule;
    }

}