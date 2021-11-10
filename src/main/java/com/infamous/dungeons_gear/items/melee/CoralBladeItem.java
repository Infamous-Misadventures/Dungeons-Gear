package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class CoralBladeItem extends SwordItem implements IMeleeWeapon, IComboWeapon {
    private final boolean unique;

    public CoralBladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.unique = isUnique;
    }

    @Override
    public boolean hasRapidSlashes(ItemStack stack) {
        return stack.getItem() instanceof CoralBladeItem;
    }

    @Override
    public boolean dealsAbsorbedDamageDuringAttackCombo(ItemStack stack) {
        return stack.getItem() == ItemRegistry.SPONGE_STRIKER.get();
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 3;
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
