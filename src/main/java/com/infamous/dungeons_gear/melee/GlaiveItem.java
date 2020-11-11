package com.infamous.dungeons_gear.melee;


import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class GlaiveItem extends SwordItem implements IMeleeWeapon {

    private final boolean unique;
    private final float attackDamage;
    private final float attackSpeed;
    private final float attackReach;
    private static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");

    public GlaiveItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float attackReachIn, Properties properties, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;
        this.attackReach = attackReachIn;
        this.unique = isUnique;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> modifierMultimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            modifierMultimap.put(AttributeRegistry.ATTACK_REACH.getName(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", (double)this.attackReach, AttributeModifier.Operation.ADDITION));
        }

        return modifierMultimap;
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

        if(stack.getItem() == DeferredItemInit.GRAVE_BANE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A relic from ages of darkness; this glaives radiates potent magical energy to ward off the undead."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage To Undead (Smite I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == DeferredItemInit.VENOM_GLAIVE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A toxic cloud seems to follow the Venom Glaive wherever it goes..."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == DeferredItemInit.GLAIVE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The glaive, wielded by the servants of the Nameless One, is a weapon with style and power."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
    }
}
