package net.gopine.modules.impl;

import net.gopine.events.manager.EventManager;
import net.gopine.listeners.ToggleSprintListener;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.modules.impl.togglesprint.GopineSprint;
import net.minecraft.client.Minecraft;

public class ToggleSprintModule extends RenderedModule {

    public static boolean sprintToggled;
    public static void setSprintToggled(boolean sprintToggled) {
        ToggleSprintModule.sprintToggled = sprintToggled;
    }

    public static int keyHoldTicks = 7;

    private Minecraft mc = Minecraft.getMinecraft();

    public ToggleSprintModule() {
        super("Togglesprint", ModuleCategory.GAMEPLAY);
    }

    @Override
    public void onModuleEnable() {
        setSprintToggled(true);
        EventManager.register(new ToggleSprintListener());
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        setSprintToggled(false);
        super.onModuleDisable();
    }

    @Override
    public void onRender(ScreenPos pos) {
        font.drawString(GopineSprint.getInstance().moduleStringDisplay(), pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth(GopineSprint.getInstance().moduleStringDisplay());
        this.approximateHeight = 10;
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        final String stringToRender1 = "[Sprinting (Toggled)]";
        font.drawString(stringToRender1, pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth(stringToRender1);
        this.approximateHeight = 10;
        super.onDummyRender(pos);
    }
}