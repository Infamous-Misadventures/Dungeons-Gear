package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.event.BowEvent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class OverchargeEnchantment extends DungeonsEnchantment {

    public OverchargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.BOW, ModEnchantmentTypes.WEAPON_SLOT);
    }

    @SubscribeEvent
    public static void onOverchargeBow(BowEvent.Overcharge event) {
        LivingEntity livingEntity = event.getEntity();
        int overchargeLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.OVERCHARGE.get(), livingEntity);
        if (overchargeLevel > 0) {
            event.setCharges(event.getCharges() + overchargeLevel);
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || (enchantment != Enchantments.QUICK_CHARGE && enchantment != EnchantmentInit.ACCELERATE.get());
    }
}
