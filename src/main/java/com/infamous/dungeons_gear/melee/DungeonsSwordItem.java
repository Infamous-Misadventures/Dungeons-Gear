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

public class DungeonsSwordItem extends net.minecraft.item.SwordItem implements IMeleeWeapon {

    private final boolean unique;

    public DungeonsSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
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

        if(stack.getItem() == DeferredItemInit.SWORD.get()){
        list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A sturdy and reliable blade."));
        }

        if(stack.getItem() == DeferredItemInit.DIAMOND_SWORD.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Diamond Sword is the true mark of a hero and an accomplished adventurer."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage (Sharpness I)"));
        }

        if(stack.getItem() == DeferredItemInit.HAWKBRAND.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Hawkbrand is the legendary sword of proven warriors."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Critical Hit Chance (Critical Hit I)"));
        }
        if(stack.getItem() == DeferredItemInit.SINISTER_SWORD.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Sinister Sword, drawn to those who face the spookiest of nights, cuts through the night with a howl."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Critical Hit Chance (Critical Hit I)"));
        }
    }
}
