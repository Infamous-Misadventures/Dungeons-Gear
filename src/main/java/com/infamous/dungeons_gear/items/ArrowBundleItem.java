package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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

    public ActionResult onItemRightClick(World world, PlayerEntity playerIn, Hand handIn) {
        ItemStack bundleItemStack = playerIn.getHeldItem(handIn);
        if (!world.isRemote) {
            PlayerEntity player = playerIn;
            int numberOfArrows = getNumberOfArrows(player, bundleItemStack);
            for (int i = 0; i < numberOfArrows; i++) {
                ItemStack itemStack = LootTableHelper.generateItemStack((ServerWorld) player.world, player.getPosition(), new ResourceLocation(MODID, "items/arrow_bundle"), player.getRNG());
                ItemEntity arrow = new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), itemStack);
                world.addEntity(arrow);
            }
            playerIn.addStat(Stats.ITEM_USED.get(this));
            if (!playerIn.abilities.isCreativeMode) {
                bundleItemStack.shrink(1);
            }
        }

        return ActionResult.func_233538_a_(bundleItemStack, world.isRemote());
    }

    private int getNumberOfArrows(PlayerEntity player, ItemStack bundleItemStack) {
        int baseArrows = DEFAULT_NUMBER_OF_ARROWS;
        if(bundleItemStack.hasTag() && bundleItemStack.getTag().contains(NUMBER_OF_ARROWS_FIELD)){
            baseArrows = bundleItemStack.getTag().getInt(NUMBER_OF_ARROWS_FIELD);
        }
        return baseArrows + StreamSupport.stream(player.getArmorInventoryList().spliterator(), false).map(this::arrowAmount).reduce(0, (a, b) -> a + b, Integer::sum);
    }

    private int arrowAmount(ItemStack stack) {
        int extraArrows = 0;
        extraArrows += EnchantmentHelper.getEnchantmentLevel(ARROW_HOARDER, stack);
        if (stack.getItem() instanceof IArmor && ((IArmor) stack.getItem()).hasArrowHoarderBuiltIn(stack)){
            extraArrows += 1;
        }
        return extraArrows;
    }


}
