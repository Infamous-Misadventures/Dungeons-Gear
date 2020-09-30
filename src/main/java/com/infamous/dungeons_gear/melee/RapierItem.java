package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class RapierItem extends SwordItem implements IMeleeWeapon {

    public RapierItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.BEE_STINGER
                || itemStack.getItem() == WeaponList.FREEZING_FOIL
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.RAPIER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The rapier, a nimble and narrow blade, strikes with quick ferocity."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Thrusts"));
        }
        if(stack.getItem() == WeaponList.BEE_STINGER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Bee Stinger, a swift blade inspired by a bee's barb, can draw friendly bees into the fray to fight alongside you."));

            //list.add(new StringTextComponent(TextFormatting.GREEN + "Chance to Summon A Bee (Busy Bee I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Thrusts"));
        }
        if(stack.getItem() == WeaponList.FREEZING_FOIL){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This needle-like blade is cold to the touch and makes quick work of any cut."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Slows Mobs (Freezing I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Thrusts"));

        }
    }
}
