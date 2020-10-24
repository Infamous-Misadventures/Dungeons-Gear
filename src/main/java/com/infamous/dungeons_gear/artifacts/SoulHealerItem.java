package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.armor.BattleRobeItem;
import com.infamous.dungeons_gear.armor.EvocationRobeItem;
import com.infamous.dungeons_gear.armor.GuardsArmorItem;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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

public class SoulHealerItem extends Item implements IArtifact, ISoulGatherer {
    public SoulHealerItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.experienceTotal >= 20 || playerIn.isCreative()){
            if(!playerIn.isCreative() && (playerIn.getHealth() < playerIn.getMaxHealth())){
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
                playerIn.giveExperiencePoints((int)(-healedAmount));
                itemstack.damageItem(1, playerIn, (entity) -> {
                    entity.sendBreakAnimation(handIn);
                });


                IArtifact.setArtifactCooldown(playerIn, itemstack.getItem(), 20);
            }
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

        if(stack.getItem() == ArtifactList.SOUL_HEALER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Soul Healer amulet is cold to the touch and trembles with the power of souls. It is a common sight among the Illagers of the Woodland Mansions."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Heals the most injured ally nearby, including yourself."));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "1 Second Cooldown"));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}
