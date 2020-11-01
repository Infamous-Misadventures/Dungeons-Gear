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

public class DeathCapMushroomItem extends ArtifactItem {
    public DeathCapMushroomItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EffectInstance haste = new EffectInstance(Effects.HASTE, 180, 3);
        EffectInstance swiftness = new EffectInstance(Effects.SPEED, 180, 1);
        playerIn.addPotionEffect(haste);
        playerIn.addPotionEffect(swiftness);

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }

        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 600);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Eaten by daring warriors before battle, the Death Cap Mushroom drives fighters into a frenzy."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Greatly increases attack and movement speed."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "9 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
    }
}
