package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class TempestKnifeItem extends SwordItem implements IMeleeWeapon, IComboWeapon {
    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 3;
    }

    private final boolean unique;

    public TempestKnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
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
        if(stack.getItem() == WeaponList.RESOLUTE_TEMPEST_KNIFE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Passed down by nomads who roam the mountain peaks, this knife has been used in countless battles."));
        }

        if(stack.getItem() == WeaponList.CHILL_GALE_KNIFE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Crafted from the never-melting ice atop the mountain peaks, this knife is forever icy to the touch"));

            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Slows Mobs (Freezing I)"));
        }
        if(stack.getItem() == WeaponList.TEMPEST_KNIFE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This knife slices through enemies like the wind that cuts between the mountaintops."));


        }
    }
}
