package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;
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

public class DungeonsPickaxeItem extends PickaxeItem implements IMeleeWeapon, IComboWeapon {
    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 1;
    }
    private final boolean unique;
    public DungeonsPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
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

        if(stack.getItem() == DeferredItemInit.PICKAXE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The pickaxe has been the iconic tool of adventurers and heroes for as long as anyone can remember."));

        }
        if(stack.getItem() == DeferredItemInit.DIAMOND_PICKAXE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Diamond is one of the most durable materials, making it an excellent choice for a pickaxe."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Finds More Valuables (Prospector I)"));
        }
    }
}
