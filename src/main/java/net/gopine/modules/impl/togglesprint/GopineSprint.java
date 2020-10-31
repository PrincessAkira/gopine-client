package net.gopine.modules.impl.togglesprint;

import net.gopine.modules.impl.ToggleSprintModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class GopineSprint extends MovementInput {

    private static GopineSprint INSTANCE = new GopineSprint();
    public static GopineSprint getInstance() {
        return INSTANCE;
    }

    private boolean sprint;
    private final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
    private int pressedSprint;
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void updatePlayerMoveState() {
        moveStrafe = 0.0f;
        moveForward = 0.0f;
        if(gameSettings.keyBindForward.isKeyDown()) {
            moveForward++;
        }
        if(gameSettings.keyBindBack.isKeyDown()) {
            moveForward--;
        }
        if(gameSettings.keyBindLeft.isKeyDown()) {
            moveStrafe++;
        }
        if(gameSettings.keyBindRight.isKeyDown()) {
            moveStrafe--;
        }
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if(sneak) {
            moveStrafe *= 0.3f;
            moveForward *= 0.3f;
        }
        if(ToggleSprintModule.sprintToggled) {
            if(gameSettings.keyBindSprint.isKeyDown()) {
                if(pressedSprint == 0) {
                    if(sprint) {
                        pressedSprint = -1;
                    } else if(mc.thePlayer.capabilities.isFlying) {
                        pressedSprint = ToggleSprintModule.keyHoldTicks + 1;
                    } else {
                        pressedSprint = 1;
                    }
                    sprint = !sprint;
                } else if(pressedSprint > 0) {
                    pressedSprint++;
                }
            } else {
                if(ToggleSprintModule.keyHoldTicks > 0 && (pressedSprint > ToggleSprintModule.keyHoldTicks))
                    sprint = false;
                pressedSprint = 0;
            }
        } else {
            sprint = false;
        }
        if(sprint && moveForward == 1.0f && this.mc.thePlayer.onGround && !this.mc.thePlayer.isUsingItem() && !this.mc.thePlayer.isPotionActive(Potion.blindness)) {
            this.mc.thePlayer.setSprinting(true);
        }
    }

    public String moduleStringDisplay() {
        String a = "";
        if(mc.thePlayer.capabilities.isFlying)
            a = "[Flying]";
        if(mc.thePlayer.isRiding())
            a = "[Riding]";
        if(gameSettings.keyBindSneak.isKeyDown()) {
            if(mc.thePlayer.capabilities.isFlying)
                a = "[Descending]";
            else if(mc.thePlayer.isRiding())
                a = "[Dismounting]";
        } else if(sprint && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.isRiding()) {
            if (gameSettings.keyBindSprint.isKeyDown())
                a = "[Sprinting (Key Held)]";
            else
                a = "[Sprinting (Toggled)]";
        }
        return a;
    }
}