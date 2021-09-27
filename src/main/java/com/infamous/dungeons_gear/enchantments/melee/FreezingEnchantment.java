package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class FreezingEnchantment extends DungeonsEnchantment {

    public FreezingEnchantment() {
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

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        ItemStack mainhand = user.getMainHandItem();
        boolean uniqueWeaponFlag = hasFreezingBuiltIn(mainhand);
        if(uniqueWeaponFlag) level++;
        EffectInstance freezing = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, level-1);
        EffectInstance miningFatigue = new EffectInstance(Effects.DIG_SLOWDOWN, 60, level-1);
        ((LivingEntity) target).addEffect(freezing);
        ((LivingEntity) target).addEffect(miningFatigue);
        PROXY.spawnParticles(target, ParticleTypes.ITEM_SNOWBALL);
    }

    private static boolean hasFreezingBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasFreezingBuiltIn(mainhand);
    }

    @SubscribeEvent
    public static void onFreezingAttack(LivingAttackEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getEntity();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getMainHandItem();
        boolean uniqueWeaponFlag = hasFreezingBuiltIn(mainhand);
        if(uniqueWeaponFlag
                && !ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.FREEZING)){
            EffectInstance freezing = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60);
            EffectInstance miningFatigue = new EffectInstance(Effects.DIG_SLOWDOWN, 60);
            victim.addEffect(freezing);
            victim.addEffect(miningFatigue);
            PROXY.spawnParticles(victim, ParticleTypes.ITEM_SNOWBALL);
        }
    }
}
