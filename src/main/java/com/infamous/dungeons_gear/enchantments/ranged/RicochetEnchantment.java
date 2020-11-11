package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class RicochetEnchantment extends Enchantment {

    public RicochetEnchantment() {
        super(Rarity.RARE, EnchantmentType.CROSSBOW, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.PIERCING;
    }


    @SubscribeEvent
    public static void onRicochetImpact(ProjectileImpactEvent.Arrow event) {
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        AbstractArrowEntity arrowEntity = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrowEntity)) return;;
        LivingEntity shooter = (LivingEntity)arrowEntity.getShooter();
        if(!(ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult))) return;
        EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult)rayTraceResult;
        LivingEntity victim = (LivingEntity) entityRayTraceResult.getEntity();

        int ricochetLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrowEntity, RangedEnchantmentList.RICOCHET);
        boolean uniqueWeaponFlag = arrowEntity.getTags().contains("LightningHarpCrossbow") || arrowEntity.getTags().contains("SlayerCrossbow");
        if(ricochetLevel > 0){
            float chainReactionRand = shooter.getRNG().nextFloat();
            if(chainReactionRand <= 0.2F * ricochetLevel){
                ProjectileEffectHelper.ricochetArrowTowardsOtherEntity(shooter, victim, arrowEntity, 10);
            }
        }
        if(uniqueWeaponFlag){
            float chainReactionRand = shooter.getRNG().nextFloat();
            if(chainReactionRand <= 0.2F){
                ProjectileEffectHelper.ricochetArrowTowardsOtherEntity(shooter, victim, arrowEntity, 10);
            }
        }
    }
}
