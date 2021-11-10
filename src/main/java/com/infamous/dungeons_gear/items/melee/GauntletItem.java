package com.infamous.dungeons_gear.items.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
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
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.invulnerableTime = 0;
        Hand hand=Hand.MAIN_HAND;
        if (stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).isPresent()) {
            if (!stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack().isEmpty()) {
                stack = stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack();
                hand=Hand.OFF_HAND;
            }
        }
        final Hand copy = hand;
        stack.hurtAndBreak(1, attacker, (p_220039_0_) -> {
            p_220039_0_.broadcastBreakEvent(copy);
        });
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity && !worldIn.isClientSide)
            update((LivingEntity) entityIn, stack, itemSlot);
    }

    public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */

    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return blockIn.getHarvestLevel() < 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING_EDGE;
    }

    @Override
    public boolean hasRelentlessCombo(ItemStack stack) {
        return stack.getItem() instanceof GauntletItem;
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
        return stack.getItem() instanceof GauntletItem;
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
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.OFF_HAND && worldIn.isClientSide) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getItemInHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        } else {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if (stack.getItem() == ItemRegistry.SOUL_FIST.get()) {
            return 2;
        } else return 0;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 0;
    }
}
