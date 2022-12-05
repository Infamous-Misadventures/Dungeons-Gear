package com.infamous.dungeons_gear.effects;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.registry.MobEffectInit;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ShieldingEffect extends MobEffect {
    public ShieldingEffect(MobEffectCategory effectType, int liquidColorIn) {
        super(effectType, liquidColorIn);
    }

    @SubscribeEvent
    public static void onShielding(ProjectileImpactEvent event){
        if(event.getRayTraceResult() instanceof EntityHitResult){
            EntityHitResult entityRayTraceResult = (EntityHitResult) event.getRayTraceResult();
            Entity entity = entityRayTraceResult.getEntity();
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) entity;
                MobEffect shielding = MobEffectInit.SHIELDING.get();
                if(livingEntity.getEffect(shielding) != null){
                    if(event.isCancelable()){
                        event.setCanceled(true);
                        if(event.getEntity() instanceof AbstractArrow){
                            AbstractArrow arrowEntity = (AbstractArrow) event.getEntity();
                            ProjectileEffectHelper.ricochetArrowLikeShield(arrowEntity, livingEntity);
                        }
                    }
                }
            }
        }
    }
}
