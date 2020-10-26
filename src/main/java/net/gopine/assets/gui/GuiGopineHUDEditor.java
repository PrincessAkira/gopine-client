package net.gopine.assets.gui;

import net.gopine.GopineClient;
import net.gopine.modules.Module;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.gopine.util.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Deprecated
public class GuiGopineHUDEditor extends GuiScreen {

    private final Map<Module, ScreenPos> modules = new HashMap<>();
    private Optional<Module> selectedModule = Optional.empty();

    private final RenderUtils renderUtils = new RenderUtils();

    private int prevX, prevY;

    public GuiGopineHUDEditor() {
        GopineClient.getInstance().getModuleManager().getModuleArray().forEach(m -> {
            this.modules.put(m, m.getScreenPos());
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.zLevel = 200;
        for(Module m : modules.keySet()) {
            ScreenPos pos = modules.get(m);
            m.onDummyRender(pos);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(selectedModule.isPresent()) {
            moveSelectedModuleRenderer(mouseX - prevX, mouseY - prevY);
        }
        this.prevX = mouseX;
        this.prevY = mouseY;
    }

    private void moveSelectedModuleRenderer(int offsetX, int offsetY) {
        Module m = selectedModule.get();
        ScreenPos pos = m.getScreenPos();
        pos.setExactPos(pos.getExactPosX() + offsetX, pos.getExactPosY() + offsetY);
    }

    @Override
    public void onGuiClosed() {
        modules.forEach((key, value) -> {
            key.pos = value;
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;

        getMousePosition(mouseX, mouseY);
    }

    private void getMousePosition(int x, int y) {
        this.selectedModule = this.modules.keySet().stream().filter(new MouseOver(x, y)).findFirst();
    }

    private class MouseOver implements Predicate<Module> {

        private final int x, y;

        public MouseOver(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean test(Module module) {

            ScreenPos pos = module.getScreenPos();

            int exactX = pos.exactPosX;
            int exactY = pos.exactPosY;

            if(x >= exactX && x <= exactX + module.approximateWidth) {
                if(y >= exactY && y <= exactY + module.approximateHeight) {
                    return true;
                }
            }

            return false;
        }

    }
}