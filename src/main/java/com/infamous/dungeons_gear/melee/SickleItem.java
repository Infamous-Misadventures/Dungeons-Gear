package com.infamous.dungeons_gear.melee;


import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
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

import java.util.List;

public class SickleItem extends HoeItem implements IOffhandAttack, IMeleeWeapon {
    public SickleItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    // This is a designated weapon, so it will not be penalized for attacking as a normal pickaxe would
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND  || equipmentSlot == EquipmentSlotType.OFFHAND?
                this.field_234674_d_ : super.getAttributeModifiers(equipmentSlot);
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.NIGHTMARES_BITE
                || itemStack.getItem() == WeaponList.THE_LAST_LAUGH
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.NIGHTMARES_BITE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The blade of Nightmare's Bite drips with deadly venom, still potent after all these years."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
        }
        if(stack.getItem() == WeaponList.THE_LAST_LAUGH){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Strange, distorted laughter seems to whisper from this menacing looking sickle."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Mobs Drop More Valuables (Prospector I)"));
        }
        if(stack.getItem() == WeaponList.SICKLE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A ceremonial weapon that hails from the same region as the Desert Temple."));

        }

        list.add(new StringTextComponent(TextFormatting.GREEN + "Dual Wield"));
    }

    @Override
    public float getOffhandAttackReach() {
        return 3.0F;
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
}
