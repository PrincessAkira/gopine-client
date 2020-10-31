package net.gopine.modules.impl;

import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ArmourEquippedDisplayModule extends RenderedModule {

    public ArmourEquippedDisplayModule() {
        super("ArmourHUD", ModuleCategory.RENDERING);
        this.approximateWidth = 64;
        this.approximateHeight = 64;
    }

    private Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onRender(ScreenPos pos) {
        for(int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {
            renderItemStack(pos, item, mc.thePlayer.inventory.armorInventory[item]);
        }
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        renderItemStack(pos,3, new ItemStack(Items.iron_helmet));
        renderItemStack(pos,2, new ItemStack(Items.iron_chestplate));
        renderItemStack(pos,1, new ItemStack(Items.iron_leggings));
        renderItemStack(pos,0, new ItemStack(Items.iron_boots));
        super.onDummyRender(pos);
    }

    public void renderItemStack(ScreenPos pos, int index, ItemStack item) {
        if(item == null) return;
        GL11.glPushMatrix();
        int offset = (-16 * index) + 48;
        if(item.getItem().isDamageable()) {
            double currentDmg = (item.getMaxDamage() - item.getItemDamage()) / (double) item.getMaxDamage() * 100;
            font.drawString(String.format("%.2f%%", currentDmg), pos.getExactPosX() + 20, pos.getExactPosY() + 5 + offset, -1);
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, pos.getExactPosX(), pos.getExactPosY() + offset);
        GL11.glPopMatrix();
    }

}