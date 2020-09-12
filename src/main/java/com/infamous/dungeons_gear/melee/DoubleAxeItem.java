package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class DoubleAxeItem extends AxeItem implements IMeleeWeapon {
    public DoubleAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    // This is a designated weapon, so it will not be penalized for attacking as a normal axe would
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.CURSED_AXE
                || itemStack.getItem() == WeaponList.WHIRLWIND
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.CURSED_AXE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This cursed, poisonous axe leaves their victims sick for years with just a single scratch."));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Defeated Mobs Explode (Exploding I)"));
        }
        if(stack.getItem() == WeaponList.WHIRLWIND){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Whirlwind, forged during an epic windstorm, is a double-bladed axe that levitates slightly."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Casts Shockwaves (Shockwave I)"));
        }
        if(stack.getItem() == WeaponList.DOUBLE_AXE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A devastating weapon fit for barbaric fighters."));

        }
        //list.add(new StringTextComponent(TextFormatting.GREEN + "Spin Attack"));
    }
}
