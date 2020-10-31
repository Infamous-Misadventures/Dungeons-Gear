package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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

public class BootsOfSwiftnessItem extends ArtifactItem {
    public BootsOfSwiftnessItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EffectInstance swiftness = new EffectInstance(Effects.SPEED, 40, 2);
        playerIn.addPotionEffect(swiftness);
        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }
        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 100);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.BOOTS_OF_SWIFTNESS){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Boots blessed with enchantments to allow for swift movements. Useful in uncertain times such as these."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Gives a short boost to movement speed."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "2 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Seconds Cooldown"));
        }
    }
}
