package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SawbladeItem extends SwordItem implements IMeleeWeapon {

    private final boolean unique;

    public SawbladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.unique = isUnique;
    }

    @Override
    public boolean hasContinuousAttacks(ItemStack stack) {
        return stack.getItem() instanceof SawbladeItem;
    }

    @Override
    public boolean canOverheat(ItemStack stack) {
        return stack.getItem() != ItemRegistry.MECHANIZED_SAWBLADE.get();
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
}
