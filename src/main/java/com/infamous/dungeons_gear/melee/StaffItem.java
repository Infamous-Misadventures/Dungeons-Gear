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

public class StaffItem extends SwordItem implements IMeleeWeapon {

    public StaffItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.BATTLESTAFF_OF_TERROR
                || itemStack.getItem() == WeaponList.GROWING_STAFF
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.BATTLESTAFF){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The battlestaff is a perfectly balanced staff that is ready for any battle."));

        }
        if(stack.getItem() == WeaponList.BATTLESTAFF_OF_TERROR){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This staff overwhelms its target in battle to explosive effect."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Defeated Mobs Explode (Exploding I)"));
        }
        if(stack.getItem() == WeaponList.GROWING_STAFF){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A staff that grows and shifts as it attacks, the Growing Staff is unpredictable and powerful."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Damage To Wounded Mobs (Committed I)"));
        }
        //list.add(new StringTextComponent(TextFormatting.GREEN + "Stylish Combo"));
    }
}
