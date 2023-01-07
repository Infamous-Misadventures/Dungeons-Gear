package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.IEmeraldsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class DeathBarterEnchantment extends DungeonsEnchantment implements IEmeraldsEnchantment {

    public DeathBarterEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onDeathBarter(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        Player player = null;
        if (living instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            return;
        }

        Inventory playerInventory = player.getInventory();
        int totalEmeraldCount = 0;
        List<Integer> emeraldSlotIndices = new ArrayList<>();
        for (int slotIndex = 0; slotIndex < playerInventory.getContainerSize(); slotIndex++) {
            ItemStack currentStack = playerInventory.getItem(slotIndex);
            if (currentStack.getItem() == Items.EMERALD) {
                totalEmeraldCount += currentStack.getCount();
                emeraldSlotIndices.add(slotIndex);
            }
        }

        int deathBarterLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DEATH_BARTER.get(), player);
        int emeraldRequirement = 150 - Math.min(100, 50 * (deathBarterLevel - 1)); // will always need at least 50 emeralds even if the level exceeds 3
        if (deathBarterLevel > 0 && totalEmeraldCount >= emeraldRequirement) {

            for (Integer slotIndex : emeraldSlotIndices) {
                if (emeraldRequirement > 0) {
                    ItemStack currentEmeraldStack = playerInventory.getItem(slotIndex);
                    int currentEmeraldCount = currentEmeraldStack.getCount();
                    if (currentEmeraldCount >= emeraldRequirement) {
                        currentEmeraldStack.setCount(currentEmeraldCount - emeraldRequirement);
                        emeraldRequirement = 0;
                    } else {
                        currentEmeraldStack.setCount(0);
                        emeraldRequirement -= currentEmeraldCount;
                    }
                } else {
                    break;
                }
            }

            event.setCanceled(true);
            event.getEntity().setHealth(1.0F);
            event.getEntity().removeAllEffects();
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 1));
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        }
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || !(enchantment instanceof IEmeraldsEnchantment);
    }

}
