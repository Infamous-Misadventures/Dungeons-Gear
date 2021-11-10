package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class DoubleAxeItem extends AxeItem implements IMeleeWeapon, IComboWeapon {
    private final boolean unique;

    public DoubleAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
    }

    // This is a designated weapon, so it will not be penalized for attacking as a normal axe would
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (p_220039_0_) -> {
            p_220039_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment.category.canEnchant(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING_EDGE)||enchantment.category.canEnchant(Items.IRON_AXE);
    }

    @Override
    public boolean hasSpinAttack(ItemStack stack) {
        return stack.getItem() instanceof DoubleAxeItem;
    }

    @Override
    public boolean hasExplodingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.CURSED_AXE.get();
    }

    @Override
    public boolean hasShockwaveBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.WHIRLWIND.get();
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 2;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(this.unique){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
