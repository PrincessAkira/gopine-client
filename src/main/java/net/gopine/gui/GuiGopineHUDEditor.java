package net.gopine.gui;

import net.gopine.GopineClient;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.DraggableElement;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.gopine.util.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class GuiGopineHUDEditor extends GuiScreen {

    private final Map<RenderedModule, ScreenPos> modules = new HashMap<>();
    private Optional<RenderedModule> selectedModule = Optional.empty();

    private boolean hovered;
    private boolean dragged = false;

    private final RenderUtils renderUtils = new RenderUtils();

    private int prevX, prevY;
    /**
     * Javadoc unused
     */
    public GuiGopineHUDEditor() {
        GopineClient.getInstance().getModuleManager().getRenderedModuleArray().forEach(m -> {
            this.modules.put(m, m.getScreenPos());
        });
    }
    /**
     * Javadoc unused
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        modules.keySet().forEach(m -> {
            ScreenPos pos = modules.get(m);
            m.onDummyRender(pos);
            fontRendererObj.drawString(m.name, pos.getExactPosX(), pos.getExactPosY() - 10, -1);
            if(m.approximateWidth != 0 && m.approximateHeight != 0) {
                renderUtils.drawHollowRect(pos.getExactPosX(), pos.getExactPosY(), m.approximateWidth, m.approximateHeight, -1);
                renderUtils.drawRect(pos.getExactPosX(), pos.getExactPosY(), pos.getExactPosX() + m.approximateWidth, pos.getExactPosY() + m.approximateHeight, new Color(255, 255, 255, 100).getRGB());
            }
        });
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    /**
     * Javadoc unused
     */
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(selectedModule.isPresent()) {
            RenderedModule m = selectedModule.get();
            ScreenPos pos = m.getScreenPos();
            if(pos.getExactPosY() < 0) {
                pos.setExactPos(pos.getExactPosX(), 10);
            }
            if(m.approximateHeight > new ScaledResolution(mc).getScaledHeight()) {
                pos.setExactPos(pos.getExactPosX(), new ScaledResolution(mc).getScaledHeight());
            }
            if(pos.getExactPosX() < 0) {
                pos.setExactPos(10, pos.getExactPosY());
            }
            if(m.approximateWidth > new ScaledResolution(mc).getScaledWidth()) {
                pos.setExactPos(new ScaledResolution(mc).getScaledWidth(), pos.getExactPosY());
            }
            pos.setExactPos(pos.getExactPosX() + mouseX - this.prevX, pos.getExactPosY() + mouseY - this.prevY);
        }
        this.prevX = mouseX;
        this.prevY = mouseY;
    }
    /**
     * Javadoc unused
     */
    @Override
    public void onGuiClosed() {
        modules.forEach((key, value) -> {
            GopineClient.getInstance().getFileHandler().saveBasicModuleData(key.name, key.category, key.toggled, value);
        });
    }
    /**
     * Javadoc unused
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;

        this.selectedModule = this.modules.keySet().stream().filter(new MouseOver(mouseX, mouseY)).findFirst();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {

        super.mouseReleased(mouseX, mouseY, state);
    }

    private static class MouseOver implements Predicate<RenderedModule> {

        private final int x, y;

        public MouseOver(int x, int y) {
            this.x = x;
            this.y = y;
        }
        /**
         * Javadoc unused
         */
        @Override
        public boolean test(RenderedModule module) {

            ScreenPos pos = module.getScreenPos();

            int exactX = pos.getExactPosX();
            int exactY = pos.getExactPosY();

            if(x >= exactX && x <= exactX + module.approximateWidth) {
                if(y >= exactY && y <= exactY + module.approximateHeight) {
                    return true;
                }
            }

            return false;
        }

    }
}