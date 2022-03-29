package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_libraries.event.BowEvent;
import com.infamous.dungeons_libraries.event.CrossbowEvent;
import com.infamous.dungeons_libraries.items.gearconfig.BowGear;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class VelocityEnchantment extends DungeonsEnchantment {

    public VelocityEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
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
        IBow weaponCapability = CapabilityHelper.getWeaponCapability(itemStack);
        if(weaponCapability == null) return;
        float bowChargeTime = weaponCapability.getBowChargeTime();
        long lastFiredTime = weaponCapability.getLastFiredTime();
        if(accelerateLevel > 0 && lastFiredTime > 0){
            event.setChargeTime(bowChargeTime);
        }
    }

}
