package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.RangedWeaponList.SNOW_BOW;
import static com.infamous.dungeons_gear.items.RangedWeaponList.WINTERS_TOUCH;
import static com.infamous.dungeons_gear.items.WeaponList.*;

@Mod.EventBusSubscriber(modid = MODID)
public class FreezingEnchantment extends Enchantment {

    public FreezingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return enchantment != Enchantments.FIRE_ASPECT;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        ItemStack mainhand = user.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == FANG_OF_FROST
                || mainhand.getItem() == FROST_SCYTHE
                || mainhand.getItem() == FREEZING_FOIL;
        if(uniqueWeaponFlag) level++;
        EffectInstance freezing = new EffectInstance(Effects.SLOWNESS, 60, level-1);
        EffectInstance miningFatigue = new EffectInstance(Effects.MINING_FATIGUE, 60, level-1);
        ((LivingEntity) target).addPotionEffect(freezing);
        ((LivingEntity) target).addPotionEffect(miningFatigue);
        PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
    }

    @SubscribeEvent
    public static void onFreezingAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == FANG_OF_FROST
                || mainhand.getItem() == FROST_SCYTHE
                || mainhand.getItem() == FREEZING_FOIL;
        if(uniqueWeaponFlag
                && !EnchantUtils.hasEnchantment(mainhand, MeleeRangedEnchantmentList.FREEZING)){
            EffectInstance freezing = new EffectInstance(Effects.SLOWNESS, 60);
            EffectInstance miningFatigue = new EffectInstance(Effects.MINING_FATIGUE, 60);
            victim.addPotionEffect(freezing);
            victim.addPotionEffect(miningFatigue);
            PROXY.spawnParticles(victim, ParticleTypes.ITEM_SNOWBALL);
        }
    }
}
