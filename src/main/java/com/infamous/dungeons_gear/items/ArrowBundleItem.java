package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.stream.StreamSupport;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.ARROW_HOARDER;

public class ArrowBundleItem  extends Item {

    private static final String NUMBER_OF_ARROWS_FIELD = "NumberOfArrows";

    private static final int DEFAULT_NUMBER_OF_ARROWS = 6;

    public ArrowBundleItem(Properties properties) {
        super(properties);
    }

    public static ItemStack changeNumberOfArrows(ItemStack itemIn, int number){
        itemIn.getOrCreateTag().putInt(NUMBER_OF_ARROWS_FIELD, number);
        return itemIn;
    }

    public InteractionResultHolder use(Level world, Player playerIn, InteractionHand handIn) {
        ItemStack bundleItemStack = playerIn.getItemInHand(handIn);
        if (!world.isClientSide) {
            Player player = playerIn;
            int numberOfArrows = getNumberOfArrows(player, bundleItemStack);
            for (int i = 0; i < numberOfArrows; i++) {
                ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) player.level, player.blockPosition(), new ResourceLocation(MODID, "items/arrow_bundle"), player.getRandom());
                ItemEntity arrow = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), itemStack);
                world.addFreshEntity(arrow);
            }
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            if (!playerIn.getAbilities().instabuild) {
                bundleItemStack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(bundleItemStack, world.isClientSide());
    }

    private int getNumberOfArrows(Player player, ItemStack bundleItemStack) {
        int baseArrows = DEFAULT_NUMBER_OF_ARROWS;
        if(bundleItemStack.hasTag() && bundleItemStack.getTag().contains(NUMBER_OF_ARROWS_FIELD)){
            baseArrows = bundleItemStack.getTag().getInt(NUMBER_OF_ARROWS_FIELD);
        }
        return baseArrows + StreamSupport.stream(player.getArmorSlots().spliterator(), false).map(this::arrowAmount).reduce(0, (a, b) -> a + b, Integer::sum);
    }

    private int arrowAmount(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(ARROW_HOARDER, stack);
    }


}
