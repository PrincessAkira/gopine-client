package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class TestModule extends Module {

    public TestModule(boolean toggled) {
        super("Test", ModuleCategory.CHAT, toggled, false);
    }

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();
    }
}