package net.gopine.modules.impl;

import net.gopine.GopineClient;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class ItemPhysicsModule extends Module {

    public static boolean shouldBeFlat;
    public static void setShouldBeFlat(boolean shouldBeFlat) {
        ItemPhysicsModule.shouldBeFlat = shouldBeFlat;
    }

    public ItemPhysicsModule() {
        super("Item Physics", ModuleCategory.GAMEPLAY);
    }

    @Override
    public void saveModuleData() {
        GopineClient.getInstance().getFileHandler().saveBasicModuleData(this.name, this.category, shouldBeFlat);
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