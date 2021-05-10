package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class DaggerItem extends SwordItem implements IOffhandAttack, IMeleeWeapon, ISoulGatherer {
    private static final UUID ATTACK_DAMAGE_MODIFIER_OFF = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A34DB5CF");
    private final boolean unique;
    private final Multimap<Attribute, AttributeModifier> attributeModifiersMain, attributeModifiersOff;

    public DaggerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> lvt_5_1_ = ImmutableMultimap.builder();
        lvt_5_1_.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        lvt_5_1_.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiersMain = lvt_5_1_.build();
        lvt_5_1_ = ImmutableMultimap.builder();
        lvt_5_1_.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER_OFF, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiersOff = lvt_5_1_.build();
    }


    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? attributeModifiersMain : equipmentSlot == EquipmentSlotType.OFFHAND ?
                attributeModifiersOff : super.getAttributeModifiers(equipmentSlot);
    }

    public Rarity getRarity(ItemStack itemStack) {

        if (this.unique) {
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DeferredItemInit.FANG_OF_FROST.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This lauded dagger of the northern mountains is known to freeze its foes to solid ice."));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Slows Mobs (Freezing I)"));
        }
        if (stack.getItem() == DeferredItemInit.MOON_DAGGER.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This curved blade shines like the crescent moon on a dark night."));

            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Souls Critical Boost (Enigma Resonator I)"));
        }
        if (stack.getItem() == DeferredItemInit.DAGGER.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Daggers are the weapon of cravens - or so folks say."));

        }
        if (stack.getItem() == DeferredItemInit.SHEAR_DAGGER.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Even the simplest of farmers can wield these Shear Daggers with savage results."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Spawn Area Damage (Swirling I)"));
        }

        list.add(new StringTextComponent(TextFormatting.GREEN + "Dual Wield"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.OFF_HAND && worldIn.isRemote && !DungeonsGearCompatibility.warDance) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getHeldItem(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        } else {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if (stack.getItem() == DeferredItemInit.MOON_DAGGER.get()) {
            return 1;
        } else return 0;
    }
}
