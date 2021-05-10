package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
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

public class DungeonsAxeItem extends AxeItem implements IMeleeWeapon, IComboWeapon {
    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 3;
    }

    private final boolean unique;
    public DungeonsAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
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

    @Override
    public boolean hasFireAspectBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.FIREBRAND.get();
    }

    @Override
    public boolean hasStunningBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.HIGHLAND_AXE.get();
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
        if(stack.getItem() == ItemRegistry.AXE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The axe is an effective weapon, favored by the relentless Vindicators of the Arch-Illager's army."));
        }
        if(stack.getItem() == ItemRegistry.FIREBRAND.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Crafted in the blackest depths of the Fiery Forge and enchanted with fiery powers."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Burns Mobs (Fire Aspect I)"));
        }
        if(stack.getItem() == ItemRegistry.HIGHLAND_AXE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Expertly crafted and a polished weapon of war, the Highland Axe also makes a daring backscratcher."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Stuns Mobs (Stunning I)"));
        }
        //list.add(new StringTextComponent(TextFormatting.GREEN + "Spin Attack"));
    }
}
