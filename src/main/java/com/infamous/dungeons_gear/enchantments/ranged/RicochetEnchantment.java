package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class RicochetEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_RICOCHET_TAG = "IntrinsicRicochet";

    public RicochetEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.CROSSBOW, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.PIERCING;
    }


    @SubscribeEvent
    public static void onRicochetImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            ;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            if (!(ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult))) return;
            EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
            LivingEntity victim = (LivingEntity) entityRayTraceResult.getEntity();

            int ricochetLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.RICOCHET);
            boolean uniqueWeaponFlag = arrow.getTags().contains(INTRINSIC_RICOCHET_TAG);
            if (ricochetLevel > 0) {
                float chainReactionRand = shooter.getRandom().nextFloat();
                if (chainReactionRand <= 0.2F * ricochetLevel) {
                    ProjectileEffectHelper.ricochetArrowTowardsOtherEntity(shooter, victim, arrow, 10);
                }
            }
            if (uniqueWeaponFlag) {
                float chainReactionRand = shooter.getRandom().nextFloat();
                if (chainReactionRand <= 0.2F) {
                    ProjectileEffectHelper.ricochetArrowTowardsOtherEntity(shooter, victim, arrow, 10);
                }
            }
        }
    }
}
