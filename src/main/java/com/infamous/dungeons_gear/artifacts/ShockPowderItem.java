package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ShockPowderItem extends ArtifactItem {
    public ShockPowderItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        AreaOfEffectHelper.stunNearbyEnemies(worldIn, playerIn);
        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }



        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 300);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Shock Powder is a reliable tool for those who wish to make a swift exit."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Stuns nearby enemies."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "15 Seconds Cooldown"));
    }
}
