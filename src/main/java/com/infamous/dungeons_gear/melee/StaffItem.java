package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class StaffItem extends TieredItem implements IMeleeWeapon, IComboWeapon {
    private final boolean unique;
    private final float attackDamage;
    private final float attackSpeed;
    private Multimap<Attribute, AttributeModifier> attributeModifierMultimap;

    public StaffItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties, boolean isUnique) {
        super(tier, properties);
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
        this.unique = isUnique;
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 10;
    }

    @Override
    public boolean shouldProcSpecialEffects(ItemStack stack, LivingEntity attacker, int combo) {
        combo -= 1;
        combo %= 10;
        return combo == 3 || combo == 6 || combo == 8 || combo == 0;
    }

    @Override
    public float damageMultiplier(ItemStack stack, LivingEntity attacker, int combo) {
        float additional = 0;
        switch (combo % 10) {
            case 3:
                additional = 0.2f;
                break;
            case 6:
                additional = 0.4f;
                break;
            case 8:
                additional = 0.6f;
                break;
            case 0:
                additional = 0.8f;
                break;

        }
        return 1 + additional;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
        return slot == EquipmentSlotType.MAINHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(slot);
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public boolean hasStylishCombo(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasExplodingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BATTLESTAFF_OF_TERROR.get();
    }

    @Override
    public boolean hasCommittedBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.GROWING_STAFF.get();
    }

    public Rarity getRarity(ItemStack itemStack) {

        if (this.unique) {
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
