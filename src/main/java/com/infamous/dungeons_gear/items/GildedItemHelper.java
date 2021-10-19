package com.infamous.dungeons_gear.items;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_libraries.capabilities.builtinenchants.BuiltInEnchantmentsHelper;
import com.infamous.dungeons_libraries.capabilities.builtinenchants.IBuiltInEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class GildedItemHelper {

    public static final ResourceLocation GILDED_ITEM_RESOURCELOCATION = new ResourceLocation(DungeonsGear.MODID, "gilded_item");

    public static ItemStack getGildedItem(Random random, ItemStack itemStack){
        LazyOptional<IBuiltInEnchantments> lazyCap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapabilityLazy(itemStack);
        lazyCap.ifPresent(cap -> {
            List<EnchantmentData> list1 = getAvailableEnchantmentResults(1, 1, itemStack, true);
            EnchantmentData randomEnchantment = WeightedRandom.getRandomItem(random, list1);
            cap.addBuiltInEnchantment(GILDED_ITEM_RESOURCELOCATION, randomEnchantment);

            itemStack.setHoverName(new TranslationTextComponent("dungeons_gear.gilded").append(" ").append(itemStack.getHoverName()));

        });
        return itemStack;
    }

    private static List<EnchantmentData> getAvailableEnchantmentResults(int minLevel, int maxLevel, ItemStack itemStack, boolean includeTreasures) {
        List<EnchantmentData> list = Lists.newArrayList();
        boolean flag = itemStack.getItem() == Items.BOOK;

        for(Enchantment enchantment : Registry.ENCHANTMENT) {
            if ((!enchantment.isTreasureOnly() || includeTreasures) && enchantment.isDiscoverable() && (enchantment.canApplyAtEnchantingTable(itemStack) || (flag && enchantment.isAllowedOnBooks()))) {
                for(int i = Math.min(enchantment.getMaxLevel(), maxLevel); i > Math.min(enchantment.getMinLevel(), minLevel) - 1; --i) {
                    list.add(new EnchantmentData(enchantment, i));
                }
            }
        }

        return list;
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event){
        LazyOptional<IBuiltInEnchantments> lazyCap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapabilityLazy(event.getItemStack());
        lazyCap.ifPresent(cap -> {
            List<EnchantmentData> builtInEnchantments = cap.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
            builtInEnchantments.forEach(enchantmentData -> {
                event.getToolTip().add(enchantmentData.enchantment.getFullname(enchantmentData.level).copy().withStyle(TextFormatting.GOLD));
            });
        });
    }

    @SubscribeEvent
    public static void onRenderTooltip(RenderTooltipEvent.Color event){
        LazyOptional<IBuiltInEnchantments> lazyCap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapabilityLazy(event.getStack());
        lazyCap.ifPresent(cap -> {
            List<EnchantmentData> builtInEnchantments = cap.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
            if(!builtInEnchantments.isEmpty()){
                event.setBorderStart(0xF0FFD700);
                event.setBorderEnd(0x50F5CC27);
                event.setBackground(0xF0AF7923);
            }
        });
    }

}
