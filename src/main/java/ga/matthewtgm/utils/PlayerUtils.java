package ga.matthewtgm.utils;

import net.gopine.mixins.renderer.RenderManagerMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

import java.lang.reflect.Method;
import java.util.Iterator;

public class PlayerUtils {

    /*public Method getMethod(Class clazz, String methodName) throws NoSuchMethodException {
        return clazz.getMethod(methodName);
        Class superClass = clazz.getSuperclass();
        if(superClass == null) {
            return null;
        } else {
            return getMethod(superClass, methodName)
        }
    }*/

    /**
     * Adds layers to the player skin (Capes, wings, etc)
     * @param layer the layer being added
     */
    /*public void addLayer(LayerRenderer layer) {

        try {

            this.getMethod(RendererLivingEntity.class, "addLayer").setAccessible(true);
            this.getMethod(RendererLivingEntity.class, "addLayer").

            for (RenderPlayer render : new RenderManagerMixin().getSkinMap().values()) {
                render.addLayer(layer);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }*/

}