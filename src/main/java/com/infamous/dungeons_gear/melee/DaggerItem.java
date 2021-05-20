package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
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
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.UUID;

public class DaggerItem extends SwordItem implements IOffhandAttack, IMeleeWeapon, ISoulGatherer, IComboWeapon {
    private static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");
    private final boolean unique;
    private final float attackDamage;
    private final float attackSpeed;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public DaggerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.unique = isUnique;
        attackDamage = attackDamageIn;
        attackSpeed = attackSpeedIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> lvt_5_1_ = ImmutableMultimap.builder();
        lvt_5_1_.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        lvt_5_1_.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = lvt_5_1_.build();
    }

    public static void setAttributeModifierMultimap(DaggerItem glaiveItem) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) glaiveItem.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) glaiveItem.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        glaiveItem.attributeModifiers = builder.build();
    }

    @Override
    public boolean canDualWield(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasFreezingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.FANG_OF_FROST.get();
    }

    @Override
    public boolean hasEnigmaResonatorBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.MOON_DAGGER.get();
    }

    @Override
    public boolean hasSwirlingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.SHEAR_DAGGER.get();
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 6;
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
        DescriptionHelper.addFullDescription(list, stack);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
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
        if (stack.getItem() == ItemRegistry.MOON_DAGGER.get()) {
            return 1;
        } else return 0;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurtResistantTime = 0;
        return super.hitEntity(stack, target, attacker);
    }
}
