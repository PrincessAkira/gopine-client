package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class ItemPhysicsModule extends Module {

    public static boolean shouldBeFlat;
    public static void setShouldBeFlat(boolean shouldBeFlat) {
        ItemPhysicsModule.shouldBeFlat = shouldBeFlat;
    }

    public ItemPhysicsModule(boolean toggled) {
        super("Item Physics", ModuleCategory.GAMEPLAY, toggled);
    }

    @Override
    public void onModuleEnable() {
        setShouldBeFlat(true);
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        setShouldBeFlat(false);
        super.onModuleDisable();
    }

}