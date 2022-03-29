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
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class AccelerateEnchantment extends DungeonsEnchantment {

    public AccelerateEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.QUICK_CHARGE;
    }

    @SubscribeEvent
    public static void onAccelerateBowFired(ArrowLooseEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        World world = livingEntity.getCommandSenderWorld();
        long worldTime = world.getGameTime();
        int charge = event.getCharge();
        ItemStack stack = event.getBow();
        if(stack.getItem() instanceof BowItem){
            IBow weaponCapability = CapabilityHelper.getWeaponCapability(stack);
            if(weaponCapability == null) return;
            long lastFiredTime = weaponCapability.getLastFiredTime();
            float bowChargeTime = weaponCapability.getBowChargeTime();

            int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

            float defaultChargeTime = 20.0F;
            float arrowVelocity = RangedAttackHelper.getVanillaArrowVelocity(livingEntity, stack, charge);
            if(stack.getItem() instanceof BowGear){
                defaultChargeTime = ((BowGear)stack.getItem()).getDefaultChargeTime();
                arrowVelocity = ((BowGear)stack.getItem()).getBowArrowVelocity(livingEntity, stack, charge);
            }

            if(accelerateLevel > 0){
                if((lastFiredTime < worldTime - (Math.max(bowChargeTime, 0) + 20) && bowChargeTime < defaultChargeTime)
                        || arrowVelocity < 1.0F){
                    weaponCapability.setBowChargeTime(defaultChargeTime);
                }
                else if(arrowVelocity == 1.0F){
                    float fireRateReduction =
                            (int)(defaultChargeTime * (0.04 + 0.04*accelerateLevel));

                    weaponCapability.setBowChargeTime(bowChargeTime - fireRateReduction);
                }
                weaponCapability.setLastFiredTime(worldTime);
            }
        }
    }

    @SubscribeEvent
    public static void onAccelerateCrossbowFired(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity playerEntity = event.getPlayer();
        World world = playerEntity.getCommandSenderWorld();
        long worldTime = world.getGameTime();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof CrossbowItem & CrossbowItem.isCharged(stack)) {
            IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
            if (weaponCap == null) return;
            long lastFiredTime = weaponCap.getLastFiredTime();
            int crossbowChargeTime = weaponCap.getCrossbowChargeTime();

            int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

            float defaultChargeTime = 25;
            if (stack.getItem() instanceof CrossbowGear) {
                defaultChargeTime = ((CrossbowGear) stack.getItem()).getDefaultChargeTime();
            }

            if (accelerateLevel > 0) {
                if (lastFiredTime < worldTime - (Math.max(crossbowChargeTime, 0) + 23)
                        && crossbowChargeTime < defaultChargeTime) {
                    weaponCap.setCrossbowChargeTime((int) defaultChargeTime);
                } else {
                    int fireRateReduction =
                            (int) (defaultChargeTime * (0.04 + 0.04 * accelerateLevel));

                    weaponCap.setCrossbowChargeTime(crossbowChargeTime - fireRateReduction);
                }
                weaponCap.setLastFiredTime(worldTime);
            }
        }
    }

    @SubscribeEvent
    public static void onBowChargeTime(BowEvent.ChargeTime event){
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
