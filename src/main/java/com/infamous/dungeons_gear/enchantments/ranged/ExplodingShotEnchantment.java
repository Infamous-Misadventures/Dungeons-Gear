package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ExplodingShotEnchantment extends DungeonsEnchantment {

    public ExplodingShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
            EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            int gravityLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.EXPLODING_SHOT);
            if (gravityLevel > 0) {
                if (event.getRayTraceResult() instanceof BlockHitResult) {
                    BlockHitResult blockRayTraceResult = (BlockHitResult) event.getRayTraceResult();
                    BlockPos blockPos = blockRayTraceResult.getBlockPos();

                    // weird arrow damage calculation from AbstractArrowEntity
                    float f = (float) arrow.getDeltaMovement().length();
                    int damage = Mth.ceil(Mth.clamp((double) f * arrow.getBaseDamage(), 0.0D, 2.147483647E9D));
                    if (arrow.isCritArrow()) {
                        long criticalDamageBonus = (long) shooter.getRandom().nextInt(damage / 2 + 2);
                        damage = (int) Math.min(criticalDamageBonus + (long) damage, 2147483647L);
                    }

                    SoundHelper.playGenericExplodeSound(arrow);
                    AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
                    AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos, damage, 3.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            if (event.getSource().getDirectEntity() instanceof AbstractArrow) {
                AbstractArrow arrowEntity = (AbstractArrow) event.getSource().getDirectEntity();
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
