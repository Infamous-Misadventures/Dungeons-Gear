package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class StunningEnchantment extends DungeonsEnchantment {

    public StunningEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        float chance = user.getRandom().nextFloat();
        if(chance <=  level * 0.05){
            MobEffectInstance stunned = new MobEffectInstance(CustomEffects.STUNNED, 60);
            MobEffectInstance nausea = new MobEffectInstance(MobEffects.CONFUSION, 60);
            MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5);
            ((LivingEntity)target).addEffect(stunned);
            ((LivingEntity)target).addEffect(nausea);
            ((LivingEntity)target).addEffect(slowness);
        }
    }
}
