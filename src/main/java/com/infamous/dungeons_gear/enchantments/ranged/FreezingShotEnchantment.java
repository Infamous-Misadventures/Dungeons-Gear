package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.FREEZING_DURATION;

@Mod.EventBusSubscriber(modid = MODID)
public class FreezingShotEnchantment extends DungeonsEnchantment {

    public FreezingShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.FIRE_ASPECT;
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        //if(!EnchantUtils.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        int freezingLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.FREEZING_SHOT);
        if(freezingLevel > 0){
            if(rayTraceResult instanceof EntityRayTraceResult) {
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) rayTraceResult;
                if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                    LivingEntity victim = (LivingEntity) ((EntityRayTraceResult) rayTraceResult).getEntity();
                    applyFreezing(victim, freezingLevel);
                }
            }
        }
    }

    private static void applyFreezing(LivingEntity target, int level) {
        EffectInstance freezing = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, FREEZING_DURATION.get(), level -1);
        EffectInstance miningFatigue = new EffectInstance(Effects.DIG_SLOWDOWN, FREEZING_DURATION.get(), level -1);
        target.addEffect(freezing);
        target.addEffect(miningFatigue);
        PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
    }
}
