package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
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

public class TormentQuiver extends Item implements IArtifact, ISoulGatherer {
    public TormentQuiver(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.experienceTotal >= 10 || playerIn.isCreative()){


            ICombo comboCap = playerIn.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            comboCap.setTormentArrowCount(3);

            if(!playerIn.isCreative()){
                playerIn.giveExperiencePoints(-10);
            }
            if(!playerIn.isCreative()){
                itemstack.damageItem(1, playerIn, (entity) -> {
                    entity.sendBreakAnimation(handIn);
                });
            }


            setArtifactCooldown(playerIn, itemstack.getItem(), 20);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.TORMENT_QUIVER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Torment Quiver radiates powerful energy drawn from the eternal source of the Undead."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Gives 3 slow arrows that knock back mobs and pass through walls."));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "Requires 10 XP"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "1 Second Cooldown"));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}
