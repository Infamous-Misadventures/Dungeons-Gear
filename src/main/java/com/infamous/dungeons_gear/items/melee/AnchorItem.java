package com.infamous.dungeons_gear.items.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

import net.minecraft.item.Item.Properties;

public class AnchorItem extends TieredItem implements IMeleeWeapon, IComboWeapon {
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER = UUID.fromString("240b88f4-95de-4153-98c4-40f35fa55b7f");

    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 1;
    }

    private final boolean unique;
    private final float attackDamage;
    private final float attackSpeed;
    private final float knockbackResistance;
    private Multimap<Attribute, AttributeModifier> attributeModifierMultimap;

    public AnchorItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float knockbackResistanceIn, Properties properties, boolean isUnique) {
        super(tier, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.knockbackResistance = knockbackResistanceIn;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER, "Weapon modifier", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
        this.unique = isUnique;
    }

    public boolean hurtEnemy(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        p_77644_1_.hurtAndBreak(1, p_77644_3_, (p_220045_0_) -> {
            p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING_EDGE;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return true;
    }

    public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
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
        return blockIn.getHarvestLevel() < 3;
    }

    @Override
    public boolean hasGreatSplash(ItemStack stack) {
        return stack.getItem() instanceof AnchorItem;
    }

    @Override
    public boolean hasGravityBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof AnchorItem;
    }

    @Override
    public boolean hasPoisonCloudBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.ENCRUSTED_ANCHOR.get();
    }

    public Rarity getRarity(ItemStack itemStack){

        if(this.unique){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
