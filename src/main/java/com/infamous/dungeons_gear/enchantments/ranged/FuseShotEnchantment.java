package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class FuseShotEnchantment extends DungeonsEnchantment {

    public static final String FUSE_SHOT_TAG = "FuseShot";

    public FuseShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }


    @SubscribeEvent
    public static void onFuseShotImpact(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (ModEnchantmentHelper.shooterIsLiving(arrow)) {
                LivingEntity shooter = (LivingEntity) arrow.getOwner();
                if (arrow.getTags().contains(FUSE_SHOT_TAG)) {
                    if (event.getRayTraceResult() instanceof BlockHitResult) {
                        BlockHitResult blockRayTraceResult = (BlockHitResult) event.getRayTraceResult();
                        BlockPos blockPos = blockRayTraceResult.getBlockPos();
                        float f = (float) arrow.getDeltaMovement().length();
                        int damage = Mth.ceil(Mth.clamp((double) f * arrow.getBaseDamage(), 0.0D, 2.147483647E9D));
                        if (arrow.isCritArrow()) {
                            long criticalDamageBonus = shooter.getRandom().nextInt(damage / 2 + 2);
                            damage = (int) Math.min(criticalDamageBonus + (long) damage, 2147483647L);
                        }
                        SoundHelper.playGenericExplodeSound(arrow);
                        AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
                        AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos, damage, 3.0F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFuseShotDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            IndirectEntityDamageSource indirectEntityDamageSource = (IndirectEntityDamageSource) event.getSource();
            if (indirectEntityDamageSource.getDirectEntity() instanceof AbstractArrow) {
                AbstractArrow arrowEntity = (AbstractArrow) indirectEntityDamageSource.getDirectEntity();

                LivingEntity victim = event.getEntity();
                if (indirectEntityDamageSource.getEntity() instanceof LivingEntity) {
                    LivingEntity archer = (LivingEntity) indirectEntityDamageSource.getEntity();
                    if (arrowEntity.getTags().contains(FUSE_SHOT_TAG)) {
                        SoundHelper.playGenericExplodeSound(arrowEntity);
                        AOECloudHelper.spawnExplosionCloud(archer, victim, 3.0f);
                        AreaOfEffectHelper.causeExplosionAttack(archer, victim, event.getAmount(), 3.0f);
                    }
                }
            }
        }
    }
}
