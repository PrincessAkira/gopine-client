package net.gopine.modules.draggable;

import net.gopine.GopineClient;
import net.gopine.assets.gui.GuiGopineHUDEditor;
import net.gopine.events.impl.client.modules.EventModuleDisable;
import net.gopine.events.impl.client.modules.EventModuleEnable;
import net.gopine.events.impl.client.modules.EventModuleToggle;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.json.simple.JSONObject;

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

    public RenderedModule(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;

        if(GopineClient.getInstance().getFileHandler().readModuleData(this.name) == null) {
            this.toggled = true;
        } else {
            toggled = (boolean) GopineClient.getInstance().getFileHandler().readModuleData(this.name).get("toggle_state");
        }

        if(this.pos == null && GopineClient.getInstance().getFileHandler().readModuleData(this.name) == null) {
            this.pos = new ScreenPos(0, 0);
        } else if(this.pos == null) {
            this.pos = new ScreenPos(0, 0);
            final JSONObject obj = (JSONObject) GopineClient.getInstance().getFileHandler().readModuleData(this.name).get("pos");
            this.pos.setExactPos(Integer.parseInt(String.valueOf(obj.get("x"))), Integer.parseInt(String.valueOf(obj.get("y"))));
        }
        if(this.approximateHeight == 0) approximateHeight = 50;
        if(this.approximateWidth == 0) approximateWidth = 50;
        this.setupModule();
    }

    /**
     * Sets up the initial module settings etc
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    private void setupModule() {
        Logger.ModLogger.info("Initializing module: " + this.name);
        try {
            this.onModuleSetup();
            this.saveModuleData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called on module setup
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public void onModuleSetup() {

    }

    /**
     * Saves the basic data of a module (NAME, CATEGORY, TOGGLE STATE, POSITIONING)
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    public void saveModuleData() {
        GopineClient.getInstance().getFileHandler().saveBasicModuleData(this.name, this.category, this.toggled, this.pos);
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