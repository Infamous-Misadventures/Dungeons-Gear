package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
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

    private final boolean unique;

    public KatanaItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(this.unique){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.DARK_KATANA.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A blade that will not rest until the battle has been won."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage To Undead (Smite I)"));
        }
        if(stack.getItem() == DeferredItemInit.MASTERS_KATANA.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Master's Katana has existed throughout the ages, appearing to heroes at the right moment."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Critical Hit Chance (Critical Hit I)"));
        }
        if(stack.getItem() == DeferredItemInit.KATANA.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A blade fit for expert warriors and fighters, its blade is crafted to inflict precision damage."));

        }
    }
}
