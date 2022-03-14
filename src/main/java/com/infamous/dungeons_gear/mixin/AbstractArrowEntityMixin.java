package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.items.artifacts.HarpoonQuiverItem;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrowEntity.class)
public abstract class AbstractArrowEntityMixin extends Entity {

    /*
    ProjectileEntity has a package-private constructor, so we extend from Entity instead
    We'll probably miss out on some ProjectileEntity methods but not needed for now
     */
    public AbstractArrowEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Inject(at = @At("RETURN"), method = "getWaterInertia", cancellable = true)
    private void checkForHarpoon(CallbackInfoReturnable<Float> cir){
        int harpoonShotLevel = ArrowHelper.enchantmentTagToLevel((AbstractArrowEntity)(Object)this, RangedEnchantmentList.EXPLODING_SHOT);
        if(this.getTags().contains(HarpoonQuiverItem.HARPOON_QUIVER)
                || harpoonShotLevel > 0){
            cir.setReturnValue(0.99F);
        }
    }
}
