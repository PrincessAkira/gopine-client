package net.gopine.events.impl.entities;

import net.gopine.events.Event;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EventEntityDeath extends Event {

    public final Entity entity;
    public final Entity killer;

    public EventEntityDeath(Entity entity, Entity killer) {
        this.entity = entity;
        this.killer = killer;
    }

}