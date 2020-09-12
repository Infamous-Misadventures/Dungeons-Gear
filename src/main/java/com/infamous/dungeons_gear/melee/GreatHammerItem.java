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

public class GreatHammerItem extends PickaxeItem implements IMeleeWeapon {
    public GreatHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    // This is a designated weapon, so it will not be penalized for attacking as a normal pickaxe would
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

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return true;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.HAMMER_OF_GRAVITY
                || itemStack.getItem() == WeaponList.STORMLANDER
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.HAMMER_OF_GRAVITY){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A hammer, embedded with a crystal that harnesses the power of gravity, that is incredibly powerful."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pulls In Enemies (Gravity I)"));
            //list.add(new StringTextComponent(TextFormatting.GREEN + "Great Splash"));
        }
        if(stack.getItem() == WeaponList.STORMLANDER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Stormlander, enchanted with the power of the raging storm, is a treasure of the Illagers."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Fires Lightning Bolts (Thundering I)"));
            //list.add(new StringTextComponent(TextFormatting.GREEN + "Great Splash"));
        }
        if(stack.getItem() == WeaponList.GREAT_HAMMER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Blacksmiths and soldiers alike use the Great Hammer for its strength in forging and in battle."));

            //list.add(new StringTextComponent(TextFormatting.GREEN + "Great Splash"));
        }
    }
}
