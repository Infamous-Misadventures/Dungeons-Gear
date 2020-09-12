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

public class MaceItem extends PickaxeItem implements IMeleeWeapon {
    public MaceItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
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
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.FLAIL
                || itemStack.getItem() == WeaponList.SUNS_GRACE
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.FLAIL){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This ancient weapon inflicts grave blunt damage to those who cannot evade the deadly metal ball."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Binds And Chains Enemies (Chains I)"));
        }
        if(stack.getItem() == WeaponList.SUNS_GRACE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This mace, engraved with secret healing runes, grants powerful restorative powers."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Heals Allies In Area (Radiance I)"));
        }
        if(stack.getItem() == WeaponList.MACE){

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Mace is a brutal tool of war and what it lacks in finesse; it makes up for in power."));

        }
    }
}
