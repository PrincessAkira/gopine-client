package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.RenderedModule;

public class EventModuleDisable extends Event {

    public Module module;
    public RenderedModule renderedModule;

    public EventModuleDisable(Module module) {
        this.module = module;
    }

    public EventModuleDisable(RenderedModule renderedModule) {
        this.renderedModule = renderedModule;
    }

}