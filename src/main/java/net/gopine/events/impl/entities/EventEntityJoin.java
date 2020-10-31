package net.gopine.events.impl.entities;

import net.gopine.events.Event;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EventEntityJoin extends Event {

    public final Entity entity;
    public final World world;

    public EventEntityJoin(Entity entity, World world)
    {
        this.entity = entity;
        this.world = world;
    }

}