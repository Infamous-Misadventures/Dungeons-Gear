package com.infamous.dungeons_gear.melee;

import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

import static com.infamous.dungeons_gear.items.WeaponList.MOON_DAGGER;

public class DaggerItem extends SwordItem implements IOffhandAttack, IMeleeWeapon, ISoulGatherer {
    public DaggerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }


    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<Attribute, AttributeModifier> attributeModifiers = ObfuscationReflectionHelper.getPrivateValue(SwordItem.class, this, "field_234810_b_");
        return equipmentSlot == EquipmentSlotType.MAINHAND  || equipmentSlot == EquipmentSlotType.OFFHAND?
                attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public Rarity getRarity(ItemStack itemStack){
        if(itemStack.getItem() == WeaponList.FANG_OF_FROST){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.FANG_OF_FROST){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This lauded dagger of the northern mountains is known to freeze its foes to solid ice."));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Slows Mobs (Freezing I)"));
        }
        if(stack.getItem() == MOON_DAGGER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This curved blade shines like the crescent moon on a dark night."));

            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Souls Critical Boost (Enigma Resonator I)"));
    }
        if(stack.getItem() == WeaponList.DAGGER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Daggers are the weapon of cravens - or so folks say."));

        }

        list.add(new StringTextComponent(TextFormatting.GREEN + "Dual Wield"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.OFF_HAND) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getHeldItem(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        } else {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if(stack.getItem() == MOON_DAGGER){
            return 1;
        }
        else return 0;
    }
}
