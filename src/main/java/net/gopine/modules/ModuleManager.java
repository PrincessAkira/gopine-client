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
        this.getModuleArray().forEach(m -> {
            if(m.isToggled()) {
                m.onModuleEnable();
            } else {
                m.onModuleDisable();
            }
        });
        Logger.info("Registered " + this.getModuleCount(false) + " modules | " + this.getModuleCount(true) + " are enabled!");
    }

    public int getModuleCount(boolean toggled) {
        if(toggled) {
            return (int) moduleArray.stream().filter(Module::isToggled).count();
        } else {
            return moduleArray.size();
        }
    }

}