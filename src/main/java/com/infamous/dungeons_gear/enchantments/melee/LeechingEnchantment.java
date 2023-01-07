package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.HealingEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class LeechingEnchantment extends HealingEnchantment {

    public LeechingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealingEnchantment);
    }

    @SubscribeEvent
    public static void onLeechingKill(LivingDeathEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrow) return;
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntity();
            ItemStack mainhand = attacker.getMainHandItem();
            if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.LEECHING.get())) {
                int leechingLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.LEECHING.get(), mainhand);
                float victimMaxHealth = victim.getMaxHealth();
                attacker.heal((0.02F + 0.02F * leechingLevel) * victimMaxHealth);
            }
        }
    }

}
