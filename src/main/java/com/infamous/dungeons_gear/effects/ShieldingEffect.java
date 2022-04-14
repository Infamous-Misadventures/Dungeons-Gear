package com.infamous.dungeons_gear.effects;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ShieldingEffect extends Effect {
    public ShieldingEffect(EffectType effectType, int liquidColorIn) {
        super(effectType, liquidColorIn);
    }

    @SubscribeEvent
    public static void onShielding(ProjectileImpactEvent event){
        if(event.getRayTraceResult() instanceof EntityRayTraceResult){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
            Entity entity = entityRayTraceResult.getEntity();
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) entity;
                Effect shielding = CustomEffects.SHIELDING;
                if(livingEntity.getEffect(shielding) != null){
                    if(event.isCancelable()){
                        event.setCanceled(true);
                        if(event.getEntity() instanceof AbstractArrowEntity){
                            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
                            ProjectileEffectHelper.ricochetArrowLikeShield(arrowEntity, livingEntity);
                        }
                    }
                }
            }
        }
    }
}
