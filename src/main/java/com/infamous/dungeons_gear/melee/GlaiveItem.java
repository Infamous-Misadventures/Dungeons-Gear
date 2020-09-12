package com.infamous.dungeons_gear.melee;


import com.infamous.dungeons_gear.interfaces.IExtendedAttackReach;
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

public class GlaiveItem extends SwordItem implements IExtendedAttackReach, IMeleeWeapon {
    public GlaiveItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.GRAVE_BANE
                || itemStack.getItem() == WeaponList.VENOM_GLAIVE
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.GRAVE_BANE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A relic from ages of darkness; this glaives radiates potent magical energy to ward off the undead."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage To Undead (Smite I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == WeaponList.VENOM_GLAIVE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A toxic cloud seems to follow the Venom Glaive wherever it goes..."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == WeaponList.GLAIVE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The glaive, wielded by the servants of the Nameless One, is a weapon with style and power."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
    }

    @Override
    public float getAttackReach() {
        return 5.0F;
    }
}
