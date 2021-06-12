package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CrossbowEvents {


    @SubscribeEvent
    public static void onAccelerateCrossbowFired(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity playerEntity = event.getPlayer();
        World world = playerEntity.getEntityWorld();
        long worldTime = world.getGameTime();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof CrossbowItem & CrossbowItem.isCharged(stack)) {
            IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
            if (weaponCap == null) return;
            long lastFiredTime = weaponCap.getLastFiredTime();
            int crossbowChargeTime = weaponCap.getCrossbowChargeTime();

            int accelerateLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);
            if (hasAccelerateBuiltIn(stack)) accelerateLevel++;

            int defaultChargeTime = 25;
            if (stack.getItem() instanceof AbstractDungeonsCrossbowItem) {
                defaultChargeTime = ((AbstractDungeonsCrossbowItem) stack.getItem()).getDefaultChargeTime();
            }

            if (accelerateLevel > 0) {
                if (lastFiredTime < worldTime - (Math.max(crossbowChargeTime, 0) + 23)
                        && crossbowChargeTime < defaultChargeTime) {
                    weaponCap.setCrossbowChargeTime(defaultChargeTime);
                } else {
                    int fireRateReduction =
                            (int) (defaultChargeTime * (0.04 + 0.04 * accelerateLevel));
                    //(int)(2.5F * accelerateLevel);

                    weaponCap.setCrossbowChargeTime(crossbowChargeTime - fireRateReduction);
                }
                weaponCap.setLastFiredTime(worldTime);
            }
        }
    }

    private static boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon) stack.getItem()).hasAccelerateBuiltIn(stack);
    }

    @SubscribeEvent
    public static void onExplodingCrossbowImpact(ProjectileImpactEvent.Arrow event) {
        AbstractArrowEntity arrowEntity = event.getArrow();
        if (arrowEntity.func_234616_v_() instanceof LivingEntity) {
            LivingEntity shooter = (LivingEntity) arrowEntity.func_234616_v_();
            boolean explodingCrossbowFlag = arrowEntity.getTags().contains(IRangedWeapon.EXPLOSIVE_TAG);
            if (explodingCrossbowFlag) {
                if (event.getRayTraceResult() instanceof BlockRayTraceResult) {
                    BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) event.getRayTraceResult();
                    BlockPos blockPos = blockRayTraceResult.getPos();

                    // weird arrow damage calculation from AbstractArrowEntity
                    float f = (float) arrowEntity.getMotion().length();
                    int damage = MathHelper.ceil(MathHelper.clamp((double) f * arrowEntity.getDamage(), 0.0D, 2.147483647E9D));
                    if (arrowEntity.getIsCritical()) {
                        long criticalDamageBonus = (long) shooter.getRNG().nextInt(damage / 2 + 2);
                        damage = (int) Math.min(criticalDamageBonus + (long) damage, 2147483647L);
                    }

                    SoundHelper.playGenericExplodeSound(arrowEntity);
                    AOECloudHelper.spawnExplosionCloudAtPos(shooter, true, blockPos, 3.0F);
                    AreaOfEffectHelper.causeExplosionAttackAtPos(shooter, true, blockPos, damage, 3.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCrossbowDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            if (event.getSource().getImmediateSource() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getSource().getImmediateSource();
                if (arrowEntity.func_234616_v_() instanceof LivingEntity) {
                    LivingEntity shooter = (LivingEntity) arrowEntity.func_234616_v_();
                    boolean explodingCrossbowFlag = arrowEntity.getTags().contains(IRangedWeapon.EXPLOSIVE_TAG);
                    if (explodingCrossbowFlag) {
                        LivingEntity victim = event.getEntityLiving();
                        SoundHelper.playGenericExplodeSound(victim);
                        AOECloudHelper.spawnExplosionCloud(shooter, victim, 3.0F);
                        AreaOfEffectHelper.causeExplosionAttack(shooter, victim, event.getAmount(), 3.0F);
                    }
                    boolean canLandMultipleHits = arrowEntity.getTags().contains(IRangedWeapon.DUAL_WIELD_TAG)
                            || arrowEntity.getTags().contains(IRangedWeapon.MULTISHOT_TAG);
                    if (canLandMultipleHits) {
                        event.getEntityLiving().hurtResistantTime = 0;
                    }
                }
            }
        }
    }
}
