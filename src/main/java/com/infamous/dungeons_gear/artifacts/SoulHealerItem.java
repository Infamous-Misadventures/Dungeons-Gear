package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.AbilityHelper;
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

public class SoulHealerItem extends ArtifactItem implements ISoulGatherer {
    public SoulHealerItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.experienceTotal >= 20 || playerIn.isCreative()){
            if((playerIn.getHealth() < playerIn.getMaxHealth())){
                float currentHealth = playerIn.getHealth();
                float maxHealth = playerIn.getMaxHealth();
                float lostHealth = maxHealth - currentHealth;
                float healedAmount;
                if(lostHealth < (maxHealth * 0.20F)){
                    playerIn.setHealth(currentHealth + lostHealth);
                    healedAmount = lostHealth;
                }
                else{
                    playerIn.setHealth(currentHealth + (maxHealth * 0.20F));
                    healedAmount = (maxHealth * 0.20F);
                }
                if(!playerIn.isCreative()){
                    playerIn.giveExperiencePoints((int)(-healedAmount));
                    itemstack.damageItem(1, playerIn, (entity) -> {
                        entity.sendBreakAnimation(handIn);
                    });
                }

                ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 20);
            }
            else{
                float healedAmount = AreaOfEffectHelper.healMostInjuredAlly(playerIn, 12);
                if(healedAmount > 0){
                    if(!playerIn.isCreative()){
                        playerIn.giveExperiencePoints((int)(-healedAmount));
                        itemstack.damageItem(1, playerIn, (entity) -> {
                            entity.sendBreakAnimation(handIn);
                        });
                    }


                    ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 20);
                }
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Soul Healer amulet is cold to the touch and trembles with the power of souls. It is a common sight among the Illagers of the Woodland Mansions."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Heals the most injured ally nearby, including yourself."));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "1 Second Cooldown"));
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}
