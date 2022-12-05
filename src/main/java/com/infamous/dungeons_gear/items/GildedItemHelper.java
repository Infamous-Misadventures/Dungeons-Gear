package com.infamous.dungeons_gear.items;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_libraries.capabilities.builtinenchants.BuiltInEnchantments;
import com.infamous.dungeons_libraries.capabilities.builtinenchants.BuiltInEnchantmentsHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID, value = Dist.CLIENT)
public class GildedItemHelper {

    public static final ResourceLocation GILDED_ITEM_RESOURCELOCATION = new ResourceLocation(DungeonsGear.MODID, "gilded_item");

    public static ItemStack getGildedItem(RandomSource random, ItemStack itemStack) {
        BuiltInEnchantments cap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapability(itemStack);
        List<EnchantmentInstance> list1 = getAvailableEnchantmentResults(1, 1, itemStack, true);
        Optional<EnchantmentInstance> randomItem = WeightedRandom.getRandomItem(random, list1, list1.size());
        randomItem.ifPresent(randomEnchantment -> {
            cap.addBuiltInEnchantment(GILDED_ITEM_RESOURCELOCATION, randomEnchantment);
            itemStack.setHoverName(Component.translatable("dungeons_gear.gilded").append(" ").append(itemStack.getHoverName()));
        });
        return itemStack;
    }

    private static List<EnchantmentInstance> getAvailableEnchantmentResults(int minLevel, int maxLevel, ItemStack itemStack, boolean includeTreasures) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        boolean flag = itemStack.getItem() == Items.BOOK;

        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if ((!enchantment.isTreasureOnly() || includeTreasures) && enchantment.isDiscoverable() && (enchantment.canApplyAtEnchantingTable(itemStack) || (flag && enchantment.isAllowedOnBooks()))) {
                for (int i = Math.min(enchantment.getMaxLevel(), maxLevel); i > Math.min(enchantment.getMinLevel(), minLevel) - 1; --i) {
                    list.add(new EnchantmentInstance(enchantment, i));
                }
            }
        }

        return list;
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        BuiltInEnchantments cap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapability(event.getItemStack());
        List<EnchantmentInstance> builtInEnchantments = cap.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
        builtInEnchantments.forEach(enchantmentData -> {
            event.getToolTip().add(enchantmentData.enchantment.getFullname(enchantmentData.level).copy().withStyle(ChatFormatting.GOLD));
        });
    }

    @SubscribeEvent
    public static void onRenderTooltip(RenderTooltipEvent.Color event) {
        BuiltInEnchantments cap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapability(event.getItemStack());
        List<EnchantmentInstance> builtInEnchantments = cap.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
        if (!builtInEnchantments.isEmpty()) {
            event.setBorderStart(0xF0FFD700);
            event.setBorderEnd(0x50F5CC27);
            event.setBackground(0xF0AF7923);
        }
    }

}
