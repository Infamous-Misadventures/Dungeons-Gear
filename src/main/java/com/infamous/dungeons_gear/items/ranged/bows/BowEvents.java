package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import com.infamous.dungeons_libraries.utils.PetHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BowEvents {


    @SubscribeEvent
    public static void onAccelerateBowFired(ArrowLooseEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        World world = livingEntity.getCommandSenderWorld();
        long worldTime = world.getGameTime();
        int charge = event.getCharge();
        ItemStack stack = event.getBow();
        if(stack.getItem() instanceof BowItem){
            IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
            if(weaponCap == null) return;
            long lastFiredTime = weaponCap.getLastFiredTime();
            float bowChargeTime = weaponCap.getBowChargeTime();

            int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);
            if(hasAccelerateBuiltIn(stack)) accelerateLevel++;

            float defaultChargeTime = 20.0F;
            float arrowVelocity = RangedAttackHelper.getVanillaArrowVelocity(livingEntity, stack, charge);
            if(stack.getItem() instanceof AbstractDungeonsBowItem){
                defaultChargeTime = ((AbstractDungeonsBowItem)stack.getItem()).getDefaultChargeTime();
                arrowVelocity = ((AbstractDungeonsBowItem)stack.getItem()).getBowArrowVelocity(stack, charge);
            }

            if(accelerateLevel > 0){
                if((lastFiredTime < worldTime - (Math.max(bowChargeTime, 0) + 20) && bowChargeTime < defaultChargeTime)
                        || arrowVelocity < 1.0F){
                    weaponCap.setBowChargeTime(defaultChargeTime);
                }
                else if(arrowVelocity == 1.0F){
                    float fireRateReduction =
                            (int)(defaultChargeTime * (0.04 + 0.04*accelerateLevel));
                            //(2.5F * accelerateLevel);

                    weaponCap.setBowChargeTime(bowChargeTime - fireRateReduction);
                }
                weaponCap.setLastFiredTime(worldTime);
            }
        }
    }

    private static boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon) stack.getItem()).hasAccelerateBuiltIn(stack);
    }


    @SubscribeEvent
    public static void onSpecialArrowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(rayTraceResult instanceof EntityRayTraceResult && ((EntityRayTraceResult) rayTraceResult).getEntity() instanceof LivingEntity){
            AbstractArrowEntity arrowEntity = event.getArrow();
            if(arrowEntity.getOwner() instanceof LivingEntity){
                LivingEntity shooter = (LivingEntity)arrowEntity.getOwner();
                LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
                boolean huntingBowFlag = arrowEntity.getTags().contains(IRangedWeapon.HUNTING_TAG);
                boolean snowBowFlag = arrowEntity.getTags().contains(IRangedWeapon.FREEZING_TAG);
                boolean trickbowFlag = arrowEntity.getTags().contains(IRangedWeapon.RELIABLE_RICOCHET_TAG);
                if(huntingBowFlag){
                    PetHelper.makeNearbyPetsAttackTarget(victim, shooter);
                }
                else if(snowBowFlag){
                    EffectInstance freezing = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 0);
                    victim.addEffect(freezing);
                    PROXY.spawnParticles(victim, ParticleTypes.ITEM_SNOWBALL);
                }
                else if(trickbowFlag){
                    ProjectileEffectHelper.ricochetArrowTowardsOtherEntity(shooter, victim, arrowEntity, 10);
                }
            }
        }
    }
}