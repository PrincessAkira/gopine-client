package net.gopine.modules.draggable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.HashMap;
import java.util.Map;

public class ScreenPos {

    public ScreenPos(int x, int y) {
        setExactPos(x, y);
    }

    public ScreenPos(float x, float y) {
        setFloatingPos(x, y);
    }

    public int exactPosX = 0, exactPosY = 0;
    public int getExactPosX() {
        return exactPosX;
    }
    public int getExactPosY() {
        return exactPosY;
    }
    public void setExactPosX(int exactPosX) {
        this.exactPosX = exactPosX;
    }
    public void setExactPosY(int exactPosY) {
        this.exactPosY = exactPosY;
    }

    public void setExactPos(int x, int y) {
        setExactPosX(x);
        setExactPosY(y);
    }

    public float floatingPosX, floatingPosY;
    public float getFloatingPosX() {
        return floatingPosX;
    }
    public float getFloatingPosY() {
        return floatingPosY;
    }
    public void setFloatingPosX(float floatingPosX) {
        this.floatingPosX = floatingPosX;
    }
    public void setFloatingPosY(float floatingPosY) {
        this.floatingPosY = floatingPosY;
    }

    public void setFloatingPos(float x, float y) {
        setFloatingPosX(x);
        setFloatingPosY(y);
    }
}