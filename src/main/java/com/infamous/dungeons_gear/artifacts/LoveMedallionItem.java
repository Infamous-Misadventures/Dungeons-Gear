package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class LoveMedallionItem extends Item implements IArtifact {
    public LoveMedallionItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        World world = playerIn.getEntityWorld();

        AreaOfEffectHelper.makeLoversOutOfNearbyEnemies(playerIn, world);

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }



        IArtifact.setArtifactCooldown(playerIn, itemstack.getItem(), 600);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.LOVE_MEDALLION){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "A spell radiates from this trinket, enchanting those nearby into a trance where they must protect the holder of the medallion at all costs."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Turn up to three hostile mobs into allies for ten seconds before they disappear."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
        }
    }
}
