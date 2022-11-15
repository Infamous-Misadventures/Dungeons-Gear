package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.bow.RangedAbilities;
import com.infamous.dungeons_gear.capabilities.bow.RangedAbilitiesHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.event.BowEvent;
import com.infamous.dungeons_libraries.event.CrossbowEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class VelocityEnchantment extends DungeonsEnchantment {

    public VelocityEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get();
    }

    @SubscribeEvent
    public static void onBowVelocity(BowEvent.Velocity event){
        ItemStack itemStack = event.getItemStack();
        int velocityLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.VELOCITY, itemStack);
        if(velocityLevel > 0){
            event.setVelocity(event.getVelocity() + 0.8F*velocityLevel);
        }
    }

    @SubscribeEvent
    public static void onCrossbowChargeTime(CrossbowEvent.ChargeTime event){
        ItemStack itemStack = event.getItemStack();
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, itemStack);
        RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(itemStack);
        float bowChargeTime = weaponCapability.getBowChargeTime();
        long lastFiredTime = weaponCapability.getLastFiredTime();
        if(accelerateLevel > 0 && lastFiredTime > 0){
            event.setChargeTime(bowChargeTime);
        }
    }

}
