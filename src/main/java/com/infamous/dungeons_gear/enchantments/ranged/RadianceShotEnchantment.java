package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class RadianceShotEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_RADIANCE_SHOT_TAG = "IntrinsicRadianceShot";

    public RadianceShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.getOwner();
        int radianceShotLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.RADIANCE_SHOT);
        if(radianceShotLevel > 0){
            float radianceShotRand = shooter.getRandom().nextFloat();
            if(radianceShotRand <=  0.2F){
                if(rayTraceResult instanceof BlockRayTraceResult){
                    BlockPos blockPos = ((BlockRayTraceResult) rayTraceResult).getBlockPos();
                    AOECloudHelper.spawnRegenCloudAtPos(shooter, true, blockPos, radianceShotLevel - 1);
                }
            }
        }
    }
}
