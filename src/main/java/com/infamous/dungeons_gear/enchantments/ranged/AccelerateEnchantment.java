package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.bow.RangedAbilities;
import com.infamous.dungeons_gear.capabilities.bow.RangedAbilitiesHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_libraries.event.BowEvent;
import com.infamous.dungeons_libraries.event.CrossbowEvent;
import com.infamous.dungeons_libraries.items.gearconfig.BowGear;
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class AccelerateEnchantment extends DungeonsEnchantment {

    public AccelerateEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.QUICK_CHARGE;
    }

    @SubscribeEvent
    public static void onAccelerateBowFired(ArrowLooseEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Level world = livingEntity.getCommandSenderWorld();
        long worldTime = world.getGameTime();
        int charge = event.getCharge();
        ItemStack stack = event.getBow();
        if (stack.getItem() instanceof BowItem) {
            RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(stack);
            long lastFiredTime = weaponCapability.getLastFiredTime();
            float bowChargeTime = weaponCapability.getBowChargeTime();

            int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), stack);

            if (accelerateLevel > 0) {
                float defaultChargeTime = 20.0F;
                float arrowVelocity = RangedAttackHelper.getArrowVelocity(livingEntity, stack, charge);
                if (stack.getItem() instanceof BowGear) {
                    defaultChargeTime = ((BowGear) stack.getItem()).getDefaultChargeTime();
                }

                if ((lastFiredTime < worldTime - (Math.max(bowChargeTime, 0) + 20) && bowChargeTime < defaultChargeTime)
                        || arrowVelocity < 1.0F) {
                    weaponCapability.setBowChargeTime(defaultChargeTime);
                } else if (arrowVelocity == 1.0F) {
                    float fireRateReduction =
                            (int) (defaultChargeTime * (0.04 + 0.04 * accelerateLevel));

                    weaponCapability.setBowChargeTime(bowChargeTime - fireRateReduction);
                }
                weaponCapability.setLastFiredTime(worldTime);
            }
        }
    }

    @SubscribeEvent
    public static void onAccelerateCrossbowFired(PlayerInteractEvent.RightClickItem event) {
        Player playerEntity = event.getEntity();
        Level world = playerEntity.getCommandSenderWorld();
        long worldTime = world.getGameTime();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof CrossbowItem & CrossbowItem.isCharged(stack)) {
            RangedAbilities weaponCap = RangedAbilitiesHelper.getRangedAbilitiesCapability(stack);
            long lastFiredTime = weaponCap.getLastFiredTime();
            int crossbowChargeTime = weaponCap.getCrossbowChargeTime();

            int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), stack);

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
    public static void onBowChargeTime(BowEvent.ChargeTime event) {
        ItemStack itemStack = event.getItemStack();
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), itemStack);
        RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(itemStack);
        float bowChargeTime = weaponCapability.getBowChargeTime();
        long lastFiredTime = weaponCapability.getLastFiredTime();
        if (accelerateLevel > 0 && lastFiredTime > 0) {
            event.setChargeTime(bowChargeTime);
        }
    }

    @SubscribeEvent
    public static void onCrossbowChargeTime(CrossbowEvent.ChargeTime event) {
        ItemStack itemStack = event.getItemStack();
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ACCELERATE.get(), itemStack);
        RangedAbilities weaponCapability = RangedAbilitiesHelper.getRangedAbilitiesCapability(itemStack);
        float bowChargeTime = weaponCapability.getBowChargeTime();
        long lastFiredTime = weaponCapability.getLastFiredTime();
        if (accelerateLevel > 0 && lastFiredTime > 0) {
            event.setChargeTime(bowChargeTime);
        }
    }

}
