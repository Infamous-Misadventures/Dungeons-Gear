package com.infamous.dungeons_gear.melee;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.UUID;

public class SickleItem extends SwordItem implements IOffhandAttack, IMeleeWeapon, IComboWeapon {
    private static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A34DB5CF");
    private final boolean unique;
    private final float attackDamage, attackSpeed;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public SickleItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> lvt_5_1_ = ImmutableMultimap.builder();
        lvt_5_1_.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        lvt_5_1_.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = lvt_5_1_.build();
        this.unique = isUnique;
    }

    public static void setAttributeModifierMultimap(SickleItem glaiveItem) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) glaiveItem.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) glaiveItem.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        glaiveItem.attributeModifiers = builder.build();
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 6;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return !playerEntity.isCreative();
    }

    public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
        if (p_150893_2_.isIn(Blocks.COBWEB) || p_150893_2_.isIn(BlockTags.LEAVES)) {
            return 15.0F;
        } else {
            Material lvt_3_1_ = p_150893_2_.getMaterial();
            return lvt_3_1_ != Material.PLANTS && lvt_3_1_ != Material.TALL_PLANTS && lvt_3_1_ != Material.CORAL && !p_150893_2_.isIn(BlockTags.LEAVES) && lvt_3_1_ != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    public boolean hitEntity(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        p_77644_2_.hurtResistantTime = 0;
        p_77644_1_.damageItem(1, p_77644_3_, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public boolean onBlockDestroyed(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
        if (p_179218_3_.getBlockHardness(p_179218_2_, p_179218_4_) != 0.0F) {
            p_179218_1_.damageItem(2, p_179218_5_, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    public boolean canHarvestBlock(BlockState p_150897_1_) {
        return p_150897_1_.isIn(Blocks.COBWEB) || p_150897_1_.isIn(BlockTags.LEAVES);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
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

        if (stack.getItem() == DeferredItemInit.NIGHTMARES_BITE.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The blade of Nightmare's Bite drips with deadly venom, still potent after all these years."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
        }
        if (stack.getItem() == DeferredItemInit.THE_LAST_LAUGH.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Strange, distorted laughter seems to whisper from this menacing looking sickle."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Mobs Drop More Valuables (Prospector I)"));
        }
        if (stack.getItem() == DeferredItemInit.SICKLE.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A ceremonial weapon that hails from the same region as the Desert Temple."));

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
}
