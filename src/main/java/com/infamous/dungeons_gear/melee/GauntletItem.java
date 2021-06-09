package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;


public class GauntletItem extends TieredItem implements IDualWieldWeapon, IVanishable, IMeleeWeapon, ISoulGatherer, IComboWeapon {
    private static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A34DB5CF");
    private final boolean unique;
    private final float attackDamage, attackSpeed;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public GauntletItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties, boolean isUnique) {
        super(tier, properties);
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        this.unique = isUnique;
    }

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return stack.getItem() == ItemRegistry.FIGHTERS_BINDING.get() ? 4 : 7;
    }

    // Only used by MobEntity class for determining sword changes
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurtResistantTime = 0;
        if (stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).isPresent()) {
            if (!stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack().isEmpty())
                stack = stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack();
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity && !worldIn.isRemote)
            update((LivingEntity) entityIn, stack, itemSlot);
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */

    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.getHarvestLevel() < 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    @Override
    public boolean hasRelentlessCombo(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasRampagingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.MAULER.get();
    }

    @Override
    public boolean hasEnigmaResonatorBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.SOUL_FIST.get();
    }

    @Override
    public boolean canDualWield(ItemStack stack) {
        return true;
    }

    @Override
    public boolean boostsAttackSpeed(ItemStack stack) {
        return stack.getItem() == ItemRegistry.FIGHTERS_BINDING.get();
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.OFF_HAND && worldIn.isRemote) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getHeldItem(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        } else {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if (stack.getItem() == ItemRegistry.SOUL_FIST.get()) {
            return 2;
        } else return 0;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 0;
    }
}
