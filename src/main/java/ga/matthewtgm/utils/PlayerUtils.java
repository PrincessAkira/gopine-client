package ga.matthewtgm.utils;

import net.gopine.mixins.renderer.RenderManagerMixin;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

import java.lang.reflect.Method;

public class PlayerUtils {

    public Method getMethod(Class clazz, String methodName, boolean fromSuperclazz) {
        if(fromSuperclazz) {
            Class superClass = clazz.getSuperclass();
            if(superClass == null) {
                return null;
            } else {
                return getMethod(superClass, methodName, false);
            }
        } else {
            try {
                return clazz.getMethod(methodName);
            } catch(Exception e) {
                return null;
            }
        }
    }

    /**
     * Adds layers to the player skin (Capes, wings, etc)
     * @param layer the layer being added
     */
    public void addLayer(LayerRenderer layer) {

        try {

            Method method = RendererLivingEntity.class.getDeclaredMethod("addLayer", LayerRenderer.class);
            method.setAccessible(true);

            for (RenderPlayer render : new RenderManagerMixin().getSkinMap().values()) {
                method.invoke(render, layer);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}