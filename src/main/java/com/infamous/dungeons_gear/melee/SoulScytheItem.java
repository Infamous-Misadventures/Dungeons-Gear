package com.infamous.dungeons_gear.melee;


import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class SoulScytheItem extends HoeItem implements IMeleeWeapon, ISoulGatherer {
    public SoulScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
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
        return enchantment.type.canEnchantItem(Items.IRON_SWORD);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.FROST_SCYTHE
                || itemStack.getItem() == WeaponList.JAILORS_SCYTHE
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        if(stack.getItem() == WeaponList.FROST_SCYTHE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Frost Scythe is an indestructible blade that is freezing to the touch and never seems to melt."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Slows Mobs (Freezing I)"));
        }
        if(stack.getItem() == WeaponList.JAILORS_SCYTHE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This scythe belonged to the terror of Highblock Keep, the Jailor."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Binds And Chains Enemies (Chains I)"));
        }

        if(stack.getItem() == WeaponList.SOUL_SCYTHE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A cruel reaper of souls, the Soul Scythe is unsentimental in its work."));

        }
        list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "+2 XP Gathering"));
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 2;
    }
}
