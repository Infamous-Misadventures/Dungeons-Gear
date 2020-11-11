package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;
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

public class ClaymoreItem extends SwordItem implements IMeleeWeapon {

    private final boolean unique;

    public ClaymoreItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
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

        if(stack.getItem() == DeferredItemInit.BROADSWORD.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Only those with the strength of a champion and the heart of a hero can carry this massive blade."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage (Sharpness I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));
        }
        if(stack.getItem() == DeferredItemInit.HEARTSTEALER.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Gifted to one of the Arch-Illager's most distinguished generals upon their conquest of the Squid Coast - this runeblade is infused with dark witchcraft."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Leeches Health From Mobs (Leeching I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));
        }
        if(stack.getItem() == DeferredItemInit.CLAYMORE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A massive sword that seems impossibly heavy yet rests easily in a just warrior's hands."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));

        }
        if(stack.getItem() == DeferredItemInit.GREAT_AXEBLADE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A lucky blacksmith turned a workshop blunder into a battlefield wonder, fusing two weapons into something new."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Jump (Dynamo I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));

        }
    }
}
