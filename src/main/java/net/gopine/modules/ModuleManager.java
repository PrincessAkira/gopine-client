package net.gopine.modules;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.client.EventRender;
import net.gopine.events.manager.EventManager;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.impl.*;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<Module> moduleArray = new ArrayList<>();
    private final List<RenderedModule> renderedModuleArray = new ArrayList<>();
    /**
     * @return an instance of the moduleArray variable
     * @author MatthewTGM
     * @since b1.0
     */
    public List<Module> getModuleArray() {
        return moduleArray;
    }

    /**
     * @return an instance of the renderedModuleArray variable
     * @author MatthewTGM
     * @since b1.0
     */
    public List<RenderedModule> getRenderedModuleArray() {
        return renderedModuleArray;
    }
    /**
     * Javadoc unused
     */
    public void declareRegularModules() {
        //this.getModuleArray().add(new TestModule());
        this.getModuleArray().add(new TNTTimerModule());
        this.getModuleArray().add(new OofModule());
        this.getModuleArray().add(new FullbrightModule());
        this.getModuleArray().add(new ItemPhysicsModule());
    }
    /**
     * Javadoc unused
     */
    public void declareRenderModules() {
        //this.getRenderedModuleArray().add(new TestModule());
        this.getRenderedModuleArray().add(new FPSDisplayModule());
        this.getRenderedModuleArray().add(new CPSDisplayModule());
        this.getRenderedModuleArray().add(new PotionEffectsDisplayModule());
        this.getRenderedModuleArray().add(new SpotifyDisplayModule());
        this.getRenderedModuleArray().add(new PingDisplayModule());
        this.getRenderedModuleArray().add(new DirectionDisplayModule());
        this.getRenderedModuleArray().add(new ToggleSprintModule());
        this.getRenderedModuleArray().add(new ArmourEquippedDisplayModule());
        this.getRenderedModuleArray().add(new DashRadioModule());
    }
    /**
     * Initializes all modules in the client
     * @author MatthewTGM
     * @since b1.0
     */
    public void initModules() {
        this.declareRegularModules();
        this.declareRenderModules();
        this.getModuleArray().forEach(m -> {
            if(m.isToggled()) {
                m.onModuleEnable();
            } else {
                m.onModuleDisable();
            }
        });
        this.getRenderedModuleArray().forEach(rm -> {
            if(rm.isToggled()) {
                rm.onModuleEnable();
            } else {
                rm.onModuleDisable();
            }
        });
        EventManager.register(this);
        Logger.ModLogger.info("Registered " + this.getModuleCount(false) + " modules | " + this.getModuleCount(true) + " are enabled!");
        Logger.ModLogger.info("Registered " + this.getRenderedModuleCount(false) + " rendered modules | " + this.getRenderedModuleCount(true) + " are enabled!");
    }
    /**
     * Javadoc unused
     */
    @EventSubscriber
    public void onRender(EventRender e) {
        if(mc.currentScreen == null  || mc.currentScreen instanceof GuiChat) {
            if(mc.thePlayer != null && mc.thePlayer.getEntityWorld() != null) {
                this.getRenderedModuleArray().forEach(m -> {
                    m.onRender(m.getScreenPos());
                });
            }
        }
    }
    /**
     * Runs a module
     * @author MatthewTGM
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
     * Runs a module
     * @author MatthewTGM
     * @since b1.0
     */
    public void runRenderedModule(RenderedModule module) {
        if(module.isToggled()) {
            module.onModuleEnable();
        } else {
            module.onModuleDisable();
        }
    }
    /**
     * @return the count of all modules currently in the client
     * @param toggled whether to return only enabled modules or disabled
     * @author MatthewTGM
     * @since b1.0
     */
    public int getModuleCount(boolean toggled) {
        if(toggled) {
            return (int) moduleArray.stream().filter(Module::isToggled).count();
        } else {
            return moduleArray.size();
        }
    }

    public int getRenderedModuleCount(boolean toggled) {
        if(toggled) {
            return (int) renderedModuleArray.stream().filter(RenderedModule::isToggled).count();
        } else {
            return renderedModuleArray.size();
        }
    }

}