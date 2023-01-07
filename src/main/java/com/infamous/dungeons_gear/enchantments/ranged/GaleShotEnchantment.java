package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class GaleShotEnchantment extends DungeonsEnchantment {

    public GaleShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            int enchantmentLevel = ArrowHelper.enchantmentTagToLevel(arrow, EnchantmentInit.GALE_SHOT.get());
            if (enchantmentLevel > 0) {
                if (rayTraceResult instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
                    if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                        LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
                        AreaOfEffectHelper.pullVictimTowardsTarget(shooter, victim, ParticleTypes.ENTITY_EFFECT, AreaOfEffectHelper.PULL_IN_SPEED_FACTOR * enchantmentLevel);
                    }
                }
            }
        }
    }
}
