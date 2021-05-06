package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
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

public class CutlassItem extends SwordItem implements IMeleeWeapon, IComboWeapon {
    private final boolean unique;

    public CutlassItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
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

        if(stack.getItem() == DeferredItemInit.DANCERS_SWORD.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Warriors who view battle as a dance with death prefer double-bladed swords."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Attack Speed (Rampaging I)"));
        }
        if(stack.getItem() == DeferredItemInit.NAMELESS_BLADE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This deadly blade's story was lost to the endless sands of time."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Weakens Enemy Attacks (Weakening I)"));
        }
        if(stack.getItem() == DeferredItemInit.CUTLASS.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This curved blade, wielded by the warriors of the Squid Coast, requires a steady hand in battle."));

            //list.add(new StringTextComponent(TextFormatting.GREEN + "Reliable Combo"));

        }
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 2;
    }
}
