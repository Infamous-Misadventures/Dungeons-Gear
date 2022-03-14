package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ExplodingShotEnchantment extends DungeonsEnchantment {

    public ExplodingShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        AbstractArrowEntity arrowEntity = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrowEntity)) return;
        LivingEntity shooter = (LivingEntity)arrowEntity.getOwner();
        int gravityLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity, RangedEnchantmentList.EXPLODING_SHOT);
        if(gravityLevel > 0){
            if (event.getRayTraceResult() instanceof BlockRayTraceResult) {
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) event.getRayTraceResult();
                BlockPos blockPos = blockRayTraceResult.getBlockPos();

                // weird arrow damage calculation from AbstractArrowEntity
                float f = (float) arrowEntity.getDeltaMovement().length();
                int damage = MathHelper.ceil(MathHelper.clamp((double) f * arrowEntity.getBaseDamage(), 0.0D, 2.147483647E9D));
                if (arrowEntity.isCritArrow()) {
                    long criticalDamageBonus = (long) shooter.getRandom().nextInt(damage / 2 + 2);
                    damage = (int) Math.min(criticalDamageBonus + (long) damage, 2147483647L);
                }

                SoundHelper.playGenericExplodeSound(arrowEntity);
                AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
                AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos, damage, 3.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onCrossbowDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            if (event.getSource().getDirectEntity() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getSource().getDirectEntity();
                if (arrowEntity.getOwner() instanceof LivingEntity) {
                    LivingEntity shooter = (LivingEntity) arrowEntity.getOwner();
                    int gravityLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity, RangedEnchantmentList.EXPLODING_SHOT);
                    if(gravityLevel > 0){
                        LivingEntity victim = event.getEntityLiving();
                        SoundHelper.playGenericExplodeSound(victim);
                        AOECloudHelper.spawnExplosionCloud(shooter, victim, 3.0F);
                        AreaOfEffectHelper.causeExplosionAttack(shooter, victim, event.getAmount(), 3.0F);
                    }
                }
            }
        }
    }
}
