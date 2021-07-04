package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
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
        return stack.getItem() instanceof ClaymoreItem;
    }

    @Override
    public boolean hasSharpnessBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BROADSWORD.get()
                || stack.getItem() == ItemRegistry.FROST_SLAYER.get();
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
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 3;
    }
}
