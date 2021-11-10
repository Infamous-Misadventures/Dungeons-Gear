package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class FuseShotEnchantment extends DungeonsEnchantment {

    public static final String FUSE_SHOT_TAG = "FuseShot";

    public FuseShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }


    @SubscribeEvent
    public static void onFuseShotImpact(ProjectileImpactEvent.Arrow event){
        AbstractArrowEntity arrowEntity = event.getArrow();
        if(ModEnchantmentHelper.shooterIsLiving(arrowEntity)){
            LivingEntity shooter =(LivingEntity) arrowEntity.getOwner();
            if(arrowEntity.getTags().contains(FUSE_SHOT_TAG)){
                if(event.getRayTraceResult() instanceof BlockRayTraceResult){
                    BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)event.getRayTraceResult();
                    BlockPos blockPos = blockRayTraceResult.getBlockPos();
                    float f = (float)arrowEntity.getDeltaMovement().length();
                    int damage = MathHelper.ceil(MathHelper.clamp((double)f * arrowEntity.getBaseDamage(), 0.0D, 2.147483647E9D));
                    if (arrowEntity.isCritArrow()) {
                        long criticalDamageBonus = (long)shooter.getRandom().nextInt(damage / 2 + 2);
                        damage = (int)Math.min(criticalDamageBonus + (long)damage, 2147483647L);
                    }
                    SoundHelper.playGenericExplodeSound(arrowEntity);
                    AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
                    AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos, damage, 3.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFuseShotDamage(LivingDamageEvent event){
        if(event.getSource() instanceof IndirectEntityDamageSource){
            IndirectEntityDamageSource indirectEntityDamageSource = (IndirectEntityDamageSource) event.getSource();
            if(indirectEntityDamageSource.getDirectEntity() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) indirectEntityDamageSource.getDirectEntity();

                LivingEntity victim = event.getEntityLiving();
                if (indirectEntityDamageSource.getEntity() instanceof LivingEntity) {
                    LivingEntity archer = (LivingEntity) indirectEntityDamageSource.getEntity();
                    if(arrowEntity.getTags().contains(FUSE_SHOT_TAG)) {
                        SoundHelper.playGenericExplodeSound(arrowEntity);
                        AOECloudHelper.spawnExplosionCloud(archer, victim, 3.0f);
                        AreaOfEffectHelper.causeExplosionAttack(archer, victim, event.getAmount(), 3.0f);
                    }
                }
            }
        }
    }
}
