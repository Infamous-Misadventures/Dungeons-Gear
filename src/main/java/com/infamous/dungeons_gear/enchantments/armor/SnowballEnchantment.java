package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class SnowballEnchantment extends PulseEnchantment {

    public SnowballEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null || player.isSpectator()) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()&&player.isEffectiveAi()) {
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;
            int snowballNearbyTimer = comboCap.getSnowballNearbyTimer();
            ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlotType.CHEST);
            boolean uniqueArmorFlag = hasSnowballBuiltIn(helmet) || hasSnowballBuiltIn(chestplate);
            if (uniqueArmorFlag || ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SNOWBALL)) {
                int snowballLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.SNOWBALL, player);
                if(uniqueArmorFlag) snowballLevel++;
                if (snowballNearbyTimer <= 0) {
                    ProjectileEffectHelper.fireSnowballAtNearbyEnemy(player, 10);
                    comboCap.setSnowballNearbyTimer(Math.max(100 - (snowballLevel - 1) * 40, 20));
                } else {
                    //if (snowballNearbyTimer == 100) snowballNearbyTimer -= Math.max(100, 20 + snowballLevel * 20);//what's this do?
                    comboCap.setSnowballNearbyTimer(Math.max(snowballNearbyTimer - 1, 0));
                }
            } else {
                if (snowballNearbyTimer != 100) {
                    comboCap.setSnowballNearbyTimer(100);
                }
            }
        }
    }

    private static boolean hasSnowballBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IArmor && ((IArmor) stack.getItem()).hasSnowballBuiltIn(stack);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof PulseEnchantment);
    }
}
