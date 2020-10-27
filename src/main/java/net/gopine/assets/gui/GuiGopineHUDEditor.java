package net.gopine.assets.gui;

import net.gopine.GopineClient;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.gopine.util.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
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
            if(m.approximateWidth != 0 && m.approximateHeight != 0) {
                renderUtils.drawHollowRect(pos.getExactPosX(), pos.getExactPosY(), m.approximateWidth, m.approximateHeight, -1);
                //renderUtils.drawRect(pos.getExactPosX(), pos.getExactPosY(), m.approximateWidth, m.approximateHeight, new Color(255, 255, 255, 133).getRGB());
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
            pos.setExactPos(pos.getExactPosX() + mouseX - prevX, pos.getExactPosY() + mouseY - prevY);
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
            key.pos = value;
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