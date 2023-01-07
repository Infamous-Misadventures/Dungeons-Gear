package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.FREEZING_DURATION;

@Mod.EventBusSubscriber(modid = MODID)
public class FreezingShotEnchantment extends DungeonsEnchantment {

    public FreezingShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.FIRE_ASPECT;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            int freezingLevel = ArrowHelper.enchantmentTagToLevel(arrow, EnchantmentInit.FREEZING_SHOT.get());
            if (freezingLevel > 0) {
                if (rayTraceResult instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
                    if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                        LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
                        applyFreezing(victim, freezingLevel);
                    }
                }
            }
        }
    }

    private static void applyFreezing(LivingEntity target, int level) {
        MobEffectInstance freezing = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, FREEZING_DURATION.get(), level - 1);
        MobEffectInstance miningFatigue = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, FREEZING_DURATION.get(), level - 1);
        target.addEffect(freezing);
        target.addEffect(miningFatigue);
        PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
    }
}
