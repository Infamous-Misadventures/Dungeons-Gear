package com.infamous.dungeons_gear.melee;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.interfaces.IExtendedAttackReach;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class SpearItem extends TieredItem implements IExtendedAttackReach, IMeleeWeapon {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifierMultimap;

    public SpearItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return !playerEntity.isCreative();
    }

    public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
        if (p_150893_2_.isIn(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material lvt_3_1_ = p_150893_2_.getMaterial();
            return lvt_3_1_ != Material.PLANTS && lvt_3_1_ != Material.TALL_PLANTS && lvt_3_1_ != Material.CORAL && !p_150893_2_.func_235714_a_(BlockTags.LEAVES) && lvt_3_1_ != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    public boolean hitEntity(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
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
        return p_150897_1_.isIn(Blocks.COBWEB);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType p_111205_1_) {
        return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(p_111205_1_);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.FORTUNE_SPEAR
                || itemStack.getItem() == WeaponList.WHISPERING_SPEAR
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        if(stack.getItem() == WeaponList.FORTUNE_SPEAR){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A spear that is watched over by lucky souls, bringing luck to any who wield it."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Mobs Drop More Loot (Fortune I)"));
        }
        if(stack.getItem() == WeaponList.WHISPERING_SPEAR){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Legend says that this cursed spear is plagued by a soul that controls the mind of any who wield it."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Sometimes Strikes Twice (Echo I)"));
        }
        if(stack.getItem() == WeaponList.SPEAR){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The spear, with its long reach and powerful range, is a solid choice of weapon."));

        }
        list.add(new StringTextComponent(TextFormatting.GREEN + "Long Melee Reach"));
    }

    @Override
    public float getAttackReach() {
        return 5.0F;
    }
}
