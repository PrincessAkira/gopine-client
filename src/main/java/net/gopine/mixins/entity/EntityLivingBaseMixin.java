package net.gopine.mixins.entity;
import net.gopine.events.impl.entities.EventEntityDeath;
import net.gopine.events.impl.player.EventAttackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/**
 * EntityPlayer mixin class used to add events.
 * @author Yukii | Azariel#0004
 * @since b0.1
 */

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {

    @Shadow
    private int maxHurtTime;
    @Shadow
    private int hurtTime;
    @Shadow
    private float attackedAtYaw;

    /**
     * Disables Hurtcam
     * @param ci unused
     * @author Yukii | Azariel#0004
     * @since b0.1
     */
    @Inject(method = "performHurtAnimation", at = @At("RETURN"))
    public void performHurtAnimation(CallbackInfo ci)
    {
        this.hurtTime = this.maxHurtTime = 0;
        this.attackedAtYaw = 0.0F;
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {
        new EventEntityDeath(source.getEntity(), source.getSourceOfDamage()).call();
    }
}
