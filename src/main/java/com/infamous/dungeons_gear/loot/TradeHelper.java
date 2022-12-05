package com.infamous.dungeons_gear.loot;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

public class TradeHelper {
    static class EnchantedItemForEmeraldsTrade implements VillagerTrades.ItemListing {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EnchantedItemForEmeraldsTrade(Item item, int price, int maxUses, int xpValue) {
            this(item, price, maxUses, xpValue, 0.05F);
        }

        public EnchantedItemForEmeraldsTrade(Item sellingItem, int price, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingStack = new ItemStack(sellingItem);
            this.emeraldCount = price;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            int lvt_3_1_ = 5 + random.nextInt(15);
            ItemStack lvt_4_1_ = EnchantmentHelper.enchantItem(random, new ItemStack(this.sellingStack.getItem()), lvt_3_1_, false);
            int lvt_5_1_ = Math.min(this.emeraldCount + lvt_3_1_, 64);
            ItemStack lvt_6_1_ = new ItemStack(Items.EMERALD, lvt_5_1_);
            return new MerchantOffer(lvt_6_1_, lvt_4_1_, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }
}
