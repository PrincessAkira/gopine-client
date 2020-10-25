package net.gopine.modules;

import net.gopine.modules.impl.OofModule;
import net.gopine.modules.impl.TNTTimerModule;
import net.gopine.modules.impl.TestModule;
import net.gopine.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> moduleArray = new ArrayList<>();

    /**
     * @return an instance of the moduleArray variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public List<Module> getModuleArray() {
        return moduleArray;
    }

    /**
     * Initializes all modules in the client
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void initModules() {
        this.getModuleArray().add(new TestModule(true));
        this.getModuleArray().add(new TNTTimerModule(true));
        this.getModuleArray().add(new OofModule(true));
        this.getModuleArray().forEach(m -> {
            if(m.isToggled()) {
                m.onModuleEnable();
            } else {
                m.onModuleDisable();
            }
        });
        Logger.ModLogger.info("Registered " + this.getModuleCount(false) + " modules | " + this.getModuleCount(true) + " are enabled!");
    }

    /**
     * Runs a module
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void runModule(Module module) {
        if(module.isToggled()) {
            module.onModuleEnable();
        } else {
            module.onModuleDisable();
        }
    }

    /**
     * @return the count of all modules currently in the client
     * @param toggled whether to return only enabled modules or disabled
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public int getModuleCount(boolean toggled) {
        if(toggled) {
            return (int) moduleArray.stream().filter(Module::isToggled).count();
        } else {
            return moduleArray.size();
        }
    }

}