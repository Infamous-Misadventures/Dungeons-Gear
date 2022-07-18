package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class TempoTheftEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_TEMPO_THEFT_TAG = "IntrinsicTempoTheft";

    public TempoTheftEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
            EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onNocturnalBowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
            int tempoTheftLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.TEMPO_THEFT);
            boolean uniqueWeaponFlag = arrow.getTags().contains(INTRINSIC_TEMPO_THEFT_TAG);
            if (tempoTheftLevel > 0 || uniqueWeaponFlag) {
                if (uniqueWeaponFlag) tempoTheftLevel++;
                if (shooter == victim) return;
                MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, tempoTheftLevel);
                MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, tempoTheftLevel);
                shooter.addEffect(speed);
                victim.addEffect(slowness);
            }
        }
    }
}
