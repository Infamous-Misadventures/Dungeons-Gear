package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class RadianceShotEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_RADIANCE_SHOT_TAG = "IntrinsicRadianceShot";

    public RadianceShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onSabrewingImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            int radianceShotLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.RADIANCE_SHOT);
            if (radianceShotLevel > 0) {
                float radianceShotRand = shooter.getRandom().nextFloat();
                if (radianceShotRand <= 0.2F) {
                    if (rayTraceResult instanceof BlockHitResult) {
                        BlockPos blockPos = ((BlockHitResult) rayTraceResult).getBlockPos();
                        AOECloudHelper.spawnRegenCloudAtPos(shooter, true, blockPos, radianceShotLevel - 1);
                    }
                }
            }
        }
    }
}
