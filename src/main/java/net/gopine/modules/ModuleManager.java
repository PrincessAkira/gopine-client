package net.gopine.modules;

import net.gopine.modules.impl.TestModule;
import net.gopine.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> moduleArray = new ArrayList<>();

    public List<Module> getModuleArray() {
        return moduleArray;
    }

    public void initModules() {
        this.moduleArray.add(new TestModule(true));
        Logger.info("Registered " + this.getModuleCount() + " modules");
    }

    public long getModuleCount() {
        return this.moduleArray.stream().filter(Module::isToggled).count();
    }

}