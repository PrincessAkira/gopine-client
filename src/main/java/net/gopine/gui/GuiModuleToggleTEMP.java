package net.gopine.gui;

import net.gopine.GopineClient;
import net.gopine.util.Logger;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiModuleToggleTEMP extends GuiScreen {

    int buttonID = 0;
    int offset = 0;

    @Override
    public void initGui() {
        GopineClient.getInstance().getModuleManager().getModuleArray().forEach(m -> {
            this.buttonList.add(new GuiButton(buttonID, this.width / 2, this.height / 2 + offset, m.name));
            buttonID = GopineClient.getInstance().getModuleManager().getModuleArray().indexOf(m);
            offset = offset + 20;
        });
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        GopineClient.getInstance().getModuleManager().getModuleArray().forEach(m -> {
            if(button.id == GopineClient.getInstance().getModuleManager().getModuleArray().indexOf(m)) {
                m.setToggled(!m.isToggled());
                if (m.isToggled()) {
                    m.onModuleEnable();
                    Logger.CustomLogger.info(m.name, "Toggled " + m.name + " to ENABLED");
                } else {
                    m.onModuleDisable();
                    Logger.CustomLogger.info(m.name, "Toggled " + m.name + " to DISABLED");
                }
            }
        });
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}