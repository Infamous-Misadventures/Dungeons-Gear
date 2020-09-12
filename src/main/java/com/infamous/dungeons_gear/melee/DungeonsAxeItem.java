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

public class DungeonsAxeItem extends AxeItem implements IMeleeWeapon {
    public DungeonsAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
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
                || itemStack.getItem() == WeaponList.FIREBRAND
                || itemStack.getItem() == WeaponList.HIGHLAND_AXE
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

        if(stack.getItem() == WeaponList.FIREBRAND){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Crafted in the blackest depths of the Fiery Forge and enchanted with fiery powers."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Burns Mobs (Fire Aspect I)"));
        }
        if(stack.getItem() == WeaponList.HIGHLAND_AXE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Expertly crafted and a polished weapon of war, the Highland Axe also makes a daring backscratcher."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Stuns Mobs (Stunning I)"));
        }
        //list.add(new StringTextComponent(TextFormatting.GREEN + "Spin Attack"));
    }
}
