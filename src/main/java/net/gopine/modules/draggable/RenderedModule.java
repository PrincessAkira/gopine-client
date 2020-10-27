package net.gopine.modules.draggable;

import net.gopine.assets.gui.GuiGopineHUDEditor;
import net.gopine.events.impl.client.modules.EventModuleDisable;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.client.modules.EventModuleToggle;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class RenderedModule {

    public String name;
    public ModuleCategory category;
    public boolean toggled;

    /**
     * @return an instance of the module toggled variable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public boolean isToggled() {
        return toggled;
    }
    /**
     * @param toggled the state of the module toggle
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public ScreenPos pos;
    public ScreenPos getScreenPos() {
        return pos;
    }
    public int approximateWidth;
    public int approximateHeight;

    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final FontRenderer font = mc.fontRendererObj;

    private List<DraggableElement> draggableElementList = new ArrayList<>();
    public List<DraggableElement> getDraggableElementList() {
        return draggableElementList;
    }

    public RenderedModule(String name, ModuleCategory category, boolean toggled) {
        this.name = name;
        this.category = category;
        this.toggled = toggled;

        if(this.pos == null) this.pos = new ScreenPos(0, 0);
        if(this.approximateHeight == 0) approximateHeight = 50;
        if(this.approximateWidth == 0) approximateWidth = 50;
        Logger.ModLogger.warn(this.name + " X:" + pos.getExactPosX());
        Logger.ModLogger.warn(this.name + " Y:" + pos.getExactPosY());
    }

    /**
     * Called on module render!
     * @param pos screen position of the mod and its draggable elements
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onRender(ScreenPos pos) {
        if(!draggableElementList.isEmpty()) {
            draggableElementList.forEach(d -> {
                if(!(Minecraft.getMinecraft().currentScreen instanceof GuiGopineHUDEditor)) {
                    d.onRender(pos);
                }
            });
        }
    }

    public void onDummyRender(ScreenPos pos) {
        if(!draggableElementList.isEmpty()) {
            draggableElementList.forEach(d -> {
                if(Minecraft.getMinecraft().currentScreen instanceof GuiGopineHUDEditor) {
                    d.onDummyRender(pos);
                }
            });
        }
    }

    /**
     * Called on module enable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleEnable() {
        new EventModuleEnable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

    /**
     * Called on module disable
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleDisable() {
        new EventModuleDisable(this).call();
        new EventModuleToggle(this, toggled).call();
    }

}