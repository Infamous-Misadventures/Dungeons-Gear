package com.infamous.dungeons_gear.artifacts;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class IronHideAmuletItem extends ArtifactItem {
    public IronHideAmuletItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EffectInstance resistance = new EffectInstance(Effects.RESISTANCE, 220, 1);
        playerIn.addPotionEffect(resistance);

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }
        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 500);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Iron Hide Amulet is both ancient and timeless. Sand mysteriously and endlessly slips through the cracks in the iron."));
            list.add(new StringTextComponent(TextFormatting.GREEN + "" + TextFormatting.ITALIC +
                    "Provides a major boost to defense for a short time."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "11 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "25 Seconds Cooldown"));
    }
}
