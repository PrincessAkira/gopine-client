package net.gopine.modules;

import net.gopine.assets.gui.GuiGopineHUDEditor;
import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.client.EventRender;
import net.gopine.events.manager.EventManager;
import net.gopine.modules.impl.*;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private Minecraft mc = Minecraft.getMinecraft();
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
        this.getModuleArray().add(new TestModule(false));
        this.getModuleArray().add(new TNTTimerModule(true));
        this.getModuleArray().add(new OofModule(true));
        this.getModuleArray().add(new FullbrightModule(true));
        this.getModuleArray().add(new FPSDisplayModule(true));
        this.getModuleArray().add(new CPSDisplayModule(true));
        this.getModuleArray().add(new PotionEffectsDisplayModule(true));
        this.getModuleArray().forEach(m -> {
            if(m.isToggled()) {
                m.onModuleEnable();
            } else {
                m.onModuleDisable();
            }
        });
        EventManager.register(this);
        Logger.ModLogger.info("Registered " + this.getModuleCount(false) + " modules | " + this.getModuleCount(true) + " are enabled!");
    }

    @EventSubscriber
    public void onRender(EventRender e) {
        if(mc.currentScreen == null  || mc.currentScreen instanceof GuiChat) {
            if(mc.thePlayer != null && mc.thePlayer.getEntityWorld() != null) {
                this.getModuleArray().forEach(m -> {
                    m.onRender(m.getScreenPos());
                });
            }
        }
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