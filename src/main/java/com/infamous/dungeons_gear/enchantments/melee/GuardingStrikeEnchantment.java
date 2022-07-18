package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class GuardingStrikeEnchantment extends AOEDamageEnchantment {

    public GuardingStrikeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onGuardingStrikeKill(LivingDeathEvent event){
        if(PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            ItemStack mainhand = attacker.getMainHandItem();
            int guardingStrikeLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.GUARDING_STRIKE, mainhand);
            if(guardingStrikeLevel > 0){
                int duration = 20 + 20 * guardingStrikeLevel;
                MobEffectInstance shield = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 2);
                attacker.addEffect(shield);
            }
        }
    }

}
