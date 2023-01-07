package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.goals.WildRageAttackGoal;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

@Mod.EventBusSubscriber(modid = MODID)
public class WildRageEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_WILD_RAGE = "IntrinsicWildRage";

    public WildRageEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR_RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND,
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPinkScoundrelImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (!ModEnchantmentHelper.arrowHitMob(rayTraceResult)) return;
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            int wildRageLevel = ArrowHelper.enchantmentTagToLevel(arrow, EnchantmentInit.WILD_RAGE.get());
            Mob victim = (Mob) ((EntityHitResult) rayTraceResult).getEntity();
            if (!(victim instanceof Enemy) || !(victim.canChangeDimensions())) return;
            if (wildRageLevel > 0) {
                float wildRageChance = 0.1F;
                wildRageChance += wildRageLevel * 0.1F;

                float chance = shooter.getRandom().nextFloat();
                if (chance <= wildRageChance) {
                    sendIntoWildRage(victim);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWildRageAttack(LivingAttackEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        LivingEntity victim = event.getEntity();
        if (!(victim instanceof Enemy) || !(victim.canChangeDimensions())) return;
        Mob enemy = (Mob) victim;
        if ((ModEnchantmentHelper.hasEnchantment(attacker, EnchantmentInit.WILD_RAGE.get()))) {
            int wildRageLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WILD_RAGE.get(), attacker);
            float wildRageChance = 0.1F;
            wildRageChance += wildRageLevel * 0.1F;

            float chance = attacker.getRandom().nextFloat();
            if (chance <= wildRageChance) {
                sendIntoWildRage(enemy);
            }
        }
    }

    public static void sendIntoWildRage(Mob mobEntity) {
        mobEntity.targetSelector.addGoal(0, new WildRageAttackGoal(mobEntity));
        PROXY.spawnParticles(mobEntity, ParticleTypes.ANGRY_VILLAGER);
    }
}
