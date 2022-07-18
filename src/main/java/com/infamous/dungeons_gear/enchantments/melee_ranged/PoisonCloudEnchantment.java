package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import com.infamous.dungeons_libraries.capabilities.timers.Timers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList.POISON_CLOUD;

@Mod.EventBusSubscriber(modid = MODID)
public class PoisonCloudEnchantment extends DungeonsEnchantment {

    public PoisonCloudEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @SubscribeEvent
    public static void onPoisonousWeaponAttack(LivingAttackEvent event) {
        if (event.getSource().getDirectEntity() != event.getSource().getEntity()) return;
        if (event.getSource() instanceof OffhandAttackDamageSource) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        if (attacker.getLastHurtMobTimestamp() == attacker.tickCount) return;
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getMainHandItem();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, POISON_CLOUD)) {
            float chance = attacker.getRandom().nextFloat();
            int level = EnchantmentHelper.getItemEnchantmentLevel(POISON_CLOUD, mainhand);
            if (chance <= DungeonsGearConfig.POISON_CLOUD_CHANCE.get() && !PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) {
                checkForPlayer(attacker);
                AOECloudHelper.spawnPoisonCloud(attacker, victim, level - 1);
            }
        }
    }

    @SubscribeEvent
    public static void onPoisonBowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if(event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();

            int poisonLevel = ArrowHelper.enchantmentTagToLevel(arrow, POISON_CLOUD);

            if (poisonLevel > 0) {
                if (rayTraceResult instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
                    if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                        LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
                        float poisonRand = shooter.getRandom().nextFloat();
                        if (poisonRand <= DungeonsGearConfig.POISON_CLOUD_CHANCE.get()) {
                            checkForPlayer(shooter);
                            AOECloudHelper.spawnPoisonCloud(shooter, victim, poisonLevel - 1);
                        }
                    }
                }
                if (rayTraceResult instanceof BlockHitResult) {
                    BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
                    BlockPos blockPos = blockRayTraceResult.getBlockPos();
                    float poisonRand = shooter.getRandom().nextFloat();
                    if (poisonRand <= 0.3F) {
                        checkForPlayer(shooter);
                        AOECloudHelper.spawnPoisonCloudAtPos(shooter, true, blockPos, poisonLevel - 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPoisonEvent(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getEffect() == MobEffects.POISON) {
            if (event.getEntityLiving() instanceof Player) {
                Player playerEntity = (Player) event.getEntityLiving();
                Timers timersCapability = TimersHelper.getTimersCapability(playerEntity);
                int enchantmentTimer = timersCapability.getEnchantmentTimer(POISON_CLOUD);
                if (enchantmentTimer > 0) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    private static void checkForPlayer(LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player playerEntity = (Player) livingEntity;
            Timers timersCapability = TimersHelper.getTimersCapability(playerEntity);
            int enchantmentTimer = timersCapability.getEnchantmentTimer(POISON_CLOUD);
            if(enchantmentTimer <= 0) {
                timersCapability.setEnchantmentTimer(POISON_CLOUD, 60);
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }
}
