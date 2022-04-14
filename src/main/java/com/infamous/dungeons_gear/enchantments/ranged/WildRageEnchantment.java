package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.goals.WildRageAttackGoal;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class WildRageEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_WILD_RAGE = "IntrinsicWildRage";

    public WildRageEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND,
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPinkScoundrelImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitMob(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.getOwner();
        int wildRageLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.WILD_RAGE);
        MobEntity victim = (MobEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        if(!(victim instanceof IMob) || !(victim.canChangeDimensions())) return;
        if(wildRageLevel > 0){
            float wildRageChance = 0.1F;
            wildRageChance += wildRageLevel * 0.1F;

            float chance = shooter.getRandom().nextFloat();
            if(chance <=  wildRageChance){
                sendIntoWildRage(victim);
            }
        }
    }

    @SubscribeEvent
    public static void onWildRageAttack(LivingAttackEvent event){
        if(!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getEntity();
        LivingEntity victim = event.getEntityLiving();
        if(!(victim instanceof IMob) || !(victim.canChangeDimensions())) return;
        MobEntity enemy = (MobEntity) victim;
            if((ModEnchantmentHelper.hasEnchantment(attacker, RangedEnchantmentList.WILD_RAGE))){
                int wildRageLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.WILD_RAGE, attacker);
                float wildRageChance = 0.1F;
                wildRageChance += wildRageLevel * 0.1F;

                float chance = attacker.getRandom().nextFloat();
                if(chance <=  wildRageChance){
                    sendIntoWildRage(enemy);
                }
            }
    }

    public static void sendIntoWildRage(MobEntity mobEntity) {
        mobEntity.targetSelector.addGoal(0, new WildRageAttackGoal(mobEntity));
        PROXY.spawnParticles(mobEntity, ParticleTypes.ANGRY_VILLAGER);
    }
}
