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

public class KatanaItem extends SwordItem implements IMeleeWeapon {

    public KatanaItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.DARK_KATANA
                || itemStack.getItem() == WeaponList.MASTERS_KATANA
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.DARK_KATANA){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A blade that will not rest until the battle has been won."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage To Undead (Smite I)"));
        }
        if(stack.getItem() == WeaponList.MASTERS_KATANA){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Master's Katana has existed throughout the ages, appearing to heroes at the right moment."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Critical Hit Chance (Critical Hit I)"));
        }
        if(stack.getItem() == WeaponList.KATANA){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A blade fit for expert warriors and fighters, its blade is crafted to inflict precision damage."));

        }
    }
}
