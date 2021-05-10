package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ClaymoreItem extends SwordItem implements IMeleeWeapon, IComboWeapon {

    private final boolean unique;

    public ClaymoreItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
    }

    @Override
    public boolean hasKnockbackBuiltIn(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasSharpnessBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BROADSWORD.get();
    }

    @Override
    public boolean hasLeechingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.HEARTSTEALER.get();
    }

    @Override
    public boolean hasDynamoBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.GREAT_AXEBLADE.get();
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

        if(stack.getItem() == ItemRegistry.BROADSWORD.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Only those with the strength of a champion and the heart of a hero can carry this massive blade."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage (Sharpness I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));
        }
        if(stack.getItem() == ItemRegistry.HEARTSTEALER.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Gifted to one of the Arch-Illager's most distinguished generals upon their conquest of the Squid Coast - this runeblade is infused with dark witchcraft."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Leeches Health From Mobs (Leeching I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));
        }
        if(stack.getItem() == ItemRegistry.CLAYMORE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A massive sword that seems impossibly heavy yet rests easily in a just warrior's hands."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));

        }
        if(stack.getItem() == ItemRegistry.GREAT_AXEBLADE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A lucky blacksmith turned a workshop blunder into a battlefield wonder, fusing two weapons into something new."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Jump (Dynamo I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Pushback"));

        }
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 3;
    }
}
