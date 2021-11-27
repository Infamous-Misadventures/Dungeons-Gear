package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.MojankHelper;
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, MojankHelper::hurtEnemyBroadcastBreakEvent);
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment.category.canEnchant(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING_EDGE)||enchantment.category.canEnchant(Items.IRON_PICKAXE);
    }

    @Override
    public boolean hasProspectorBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.DIAMOND_PICKAXE.get();
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
