package net.gopine.mixins.entity;


import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;


/**
 * Stuff needed for MousedelayFix
 * @author Yukii Kamouto | Azariel#0004
 * @since b0.1
 */
@Mixin(Entity.class)
public class EntityMixin {

    public float rotationYawHead;
    public float rotationPitch;
    public float prevRotationPitch;
    public float prevRotationYawHead;
    public float rotationYaw;
    public float prevRotationYaw;

    /**
     * Stuff needed for MousedelayFix
     * @author Yukii Kamouto | Azariel#0004
     * @since b0.1
     */
    public Vec3 getLook(float partialTicks)
    {
        // System.out.println("MouseDelayFix Loaded");
        if (partialTicks == 1.0F)
        {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
        }
        else
        {
            float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
            float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
            return this.getVectorForRotation(f, f1);
        }
    }
    /**
     * Stuff needed for MousedelayFix
     * @author Yukii Kamouto | Azariel#0004
     * @since b0.1
     */
    protected final Vec3 getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }


}