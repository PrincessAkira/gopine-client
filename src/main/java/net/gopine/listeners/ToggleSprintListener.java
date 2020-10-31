package net.gopine.listeners;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.client.EventTick;
import net.gopine.events.impl.world.EventWorldJoin;
import net.gopine.modules.impl.togglesprint.GopineSprint;
import net.minecraft.client.Minecraft;

public class ToggleSprintListener {

    @EventSubscriber
    public void onWorldJoin(EventWorldJoin e) {
        if(Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.movementInput = new GopineSprint();
        }
    }

}