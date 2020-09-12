package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.interfaces.IExtendedAttackReach;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class WhipItem extends SwordItem implements IExtendedAttackReach, IMeleeWeapon {
    public WhipItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.VINE_WHIP
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.WHIP){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A whip made of sturdy rope, very dangerous in the right hands."));

        }

        if(stack.getItem() == WeaponList.VINE_WHIP){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A sturdy whip made from thick, thorn-laden vines capable of poisoning anything it touches. Be careful not to scratch yourself!"));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
        }

        list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));

    }

    @Override
    public float getAttackReach() {
        return 5.0F;
    }
}
