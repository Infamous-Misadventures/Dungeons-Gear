package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.capabilities.timers.ITimers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList.POISON_CLOUD;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class PoisonCloudEnchantment extends DungeonsEnchantment {

    public PoisonCloudEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
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
                AOECloudHelper.spawnPoisonCloud(attacker, victim, level);
            }
        }
    }

    @SubscribeEvent
    public static void onPoisonBowImpact(ProjectileImpactEvent.Arrow event) {
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        //if(!EnchantUtils.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity) arrow.getOwner();

        int poisonLevel = ArrowHelper.enchantmentTagToLevel(arrow, POISON_CLOUD);

        if (poisonLevel > 0) {
            if (rayTraceResult instanceof EntityRayTraceResult) {
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) rayTraceResult;
                if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                    LivingEntity victim = (LivingEntity) ((EntityRayTraceResult) rayTraceResult).getEntity();
                    float poisonRand = shooter.getRandom().nextFloat();
                    if (poisonRand <= DungeonsGearConfig.POISON_CLOUD_CHANCE.get()) {
                        checkForPlayer(shooter);
                        AOECloudHelper.spawnPoisonCloud(shooter, victim, poisonLevel - 1);
                    }
                }
            }
            if (rayTraceResult instanceof BlockRayTraceResult) {
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
                BlockPos blockPos = blockRayTraceResult.getBlockPos();
                float poisonRand = shooter.getRandom().nextFloat();
                if (poisonRand <= 0.3F) {
                    checkForPlayer(shooter);
                    AOECloudHelper.spawnPoisonCloudAtPos(shooter, true, blockPos, poisonLevel - 1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPoisonEvent(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getEffect() == Effects.POISON) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                ITimers timersCapability = TimersHelper.getTimersCapability(playerEntity);
                int enchantmentTimer = timersCapability.getEnchantmentTimer(POISON_CLOUD);
                if (enchantmentTimer > 0) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    private static void checkForPlayer(LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            ITimers timersCapability = TimersHelper.getTimersCapability(playerEntity);
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
