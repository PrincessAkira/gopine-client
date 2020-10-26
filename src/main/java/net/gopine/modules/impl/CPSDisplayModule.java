package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.ScreenPos;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class CPSDisplayModule extends Module {

    private ArrayList<Long> leftClicks = new ArrayList<>();
    private ArrayList<Long> rightClicks = new ArrayList<>();

    private boolean leftWasPressed = false;
    private long leftLastPressed;

    private boolean rightWasPressed = false;
    private long rightLastPressed;

    public CPSDisplayModule(boolean toggled) {
        super("CPS", ModuleCategory.RENDERING, toggled, true);
        this.approximateHeight = 10;
    }

    @Override
    public void onRender(ScreenPos pos) {
        font.drawString("[" + this.getPlayerLeftCPS() + " | " + this.getPlayerRightCPS() + "]", pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth("[" + this.getPlayerLeftCPS() + " | " + this.getPlayerRightCPS() + "]");
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        font.drawString("[000 | 000]", pos.getExactPosX(), pos.getExactPosY(), -1);
        this.approximateWidth = font.getStringWidth("[000 | 000]");
        super.onDummyRender(pos);
    }

    private int getPlayerLeftCPS() {

        final boolean pressed = Mouse.isButtonDown(0);

        if(pressed != leftWasPressed) {
            leftWasPressed = pressed;
            leftLastPressed = System.currentTimeMillis();
            if(pressed) {
                this.leftClicks.add(leftLastPressed);
            }
        }

        this.leftClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
        return this.leftClicks.size();
    }

    private int getPlayerRightCPS() {
        final boolean pressed = Mouse.isButtonDown(1);

        if(pressed != rightWasPressed) {
            rightWasPressed = pressed;
            rightLastPressed = System.currentTimeMillis();
            if(pressed) {
                this.rightClicks.add(rightLastPressed);
            }
        }

        this.rightClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
        return this.rightClicks.size();
    }

}