package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class GuardingStrikeEnchantment extends AOEDamageEnchantment {

    public GuardingStrikeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
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
                EffectInstance shield = new EffectInstance(Effects.DAMAGE_RESISTANCE, duration, 2);
                attacker.addEffect(shield);
            }
        }
    }

}
