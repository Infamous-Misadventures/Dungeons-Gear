package com.infamous.dungeons_gear.melee;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.interfaces.IExtendedAttackReach;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
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
    private final float attackDamage;
    private final float attackSpeed;
    private final float attackReach;
    private Multimap<Attribute, AttributeModifier> attributeModifierMultimap;
    private static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");

    public GlaiveItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float attackReachIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;
        this.attackReach = attackReachIn;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
    }

    public static void setAttributeModifierMultimap(GlaiveItem glaiveItem){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)glaiveItem.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)glaiveItem.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", (double)glaiveItem.attackReach, AttributeModifier.Operation.ADDITION));
        glaiveItem.attributeModifierMultimap = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(equipmentSlot);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.GRAVE_BANE
                || itemStack.getItem() == WeaponList.VENOM_GLAIVE
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.GRAVE_BANE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A relic from ages of darkness; this glaives radiates potent magical energy to ward off the undead."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage To Undead (Smite I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == WeaponList.VENOM_GLAIVE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A toxic cloud seems to follow the Venom Glaive wherever it goes..."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
        if(stack.getItem() == WeaponList.GLAIVE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The glaive, wielded by the servants of the Nameless One, is a weapon with style and power."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Longer Melee Reach"));
        }
    }
}
