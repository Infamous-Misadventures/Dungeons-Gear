package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class StaffItem extends TieredItem implements IMeleeWeapon {

    private final boolean unique;

    private final float attackDamage;
    private final float attackSpeed;
    private Multimap<Attribute, AttributeModifier> attributeModifierMultimap;
    public StaffItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties, boolean isUnique) {
        super(tier, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
        this.unique = isUnique;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
        return slot == EquipmentSlotType.MAINHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(slot);
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

        if(stack.getItem() == DeferredItemInit.BATTLESTAFF.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The battlestaff is a perfectly balanced staff that is ready for any battle."));

        }
        if(stack.getItem() == DeferredItemInit.BATTLESTAFF_OF_TERROR.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This staff overwhelms its target in battle to explosive effect."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Defeated Mobs Explode (Exploding I)"));
        }
        if(stack.getItem() == DeferredItemInit.GROWING_STAFF.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A staff that grows and shifts as it attacks, the Growing Staff is unpredictable and powerful."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Damage To Wounded Mobs (Committed I)"));
        }
        //list.add(new StringTextComponent(TextFormatting.GREEN + "Stylish Combo"));
    }
}
