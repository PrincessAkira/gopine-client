package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class TestModule extends Module {

    public TestModule() {
        super("Test", ModuleCategory.CHAT, true, false);
    }

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();
    }
}