package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.items.artifacts.HarpoonQuiverItem;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowEntityMixin extends Entity {

    /*
    ProjectileEntity has a package-private constructor, so we extend from Entity instead
    We'll probably miss out on some ProjectileEntity methods but not needed for now
     */
    public AbstractArrowEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Inject(at = @At("RETURN"), method = "getWaterInertia", cancellable = true)
    private void checkForHarpoon(CallbackInfoReturnable<Float> cir) {
        int harpoonShotLevel = ArrowHelper.enchantmentTagToLevel((AbstractArrow) (Object) this, EnchantmentInit.HARPOON_SHOT.get());
        if (this.getTags().contains(HarpoonQuiverItem.HARPOON_QUIVER)
                || harpoonShotLevel > 0) {
            cir.setReturnValue(0.99F);
        }
    }
}
