package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class FlamingQuiver extends Item implements IArtifact {
    public FlamingQuiver(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        ICombo comboCap = playerIn.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
        comboCap.setFlamingArrowsCount(7);

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

        if(stack.getItem() == ArtifactList.FLAMING_QUIVER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "This quiver is filled with the deadliest of arrows."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Gives you 7 burning arrows."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
        }
    }
}
