package net.gopine.modules.impl;

import net.gopine.GopineClient;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class TNTTimerModule extends Module {

    public static boolean shouldRenderTag;
    public static void setShouldRenderTag(boolean shouldRenderTag) {
        TNTTimerModule.shouldRenderTag = shouldRenderTag;
    }

    public TNTTimerModule() {
        super("TNT Timer", ModuleCategory.GAMEPLAY);
    }

    @Override
    public void saveModuleData() {
        GopineClient.getInstance().getFileHandler().saveBasicModuleData(this.name, this.category, shouldRenderTag);
    }

    @Override
    public void onModuleEnable() {
        setShouldRenderTag(true);
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        setShouldRenderTag(false);
        super.onModuleDisable();
    }

}