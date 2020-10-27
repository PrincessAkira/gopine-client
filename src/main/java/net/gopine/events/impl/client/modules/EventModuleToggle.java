package net.gopine.events.impl.client.modules;

import net.gopine.events.Event;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.RenderedModule;

public class EventModuleToggle extends Event {

    public Module module;
    public RenderedModule renderedModule;
    public boolean state;

    public EventModuleToggle(Module module, boolean state) {
        this.module = module;
        this.state = state;
    }

    public EventModuleToggle(RenderedModule renderedModule, boolean state) {
        this.renderedModule = renderedModule;
        this.state = state;
    }

}