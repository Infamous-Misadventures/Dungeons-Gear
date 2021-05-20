package com.infamous.dungeons_gear.melee;


import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
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

public class SoulScytheItem extends SwordItem implements IMeleeWeapon, ISoulGatherer, IComboWeapon {
    @Override
    public int getComboLength(ItemStack stack, LivingEntity attacker) {
        return 2;
    }

    private final boolean unique;
    public SoulScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties, boolean isUnique) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.unique = isUnique;
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

    @Override
    public boolean hasFreezingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.FROST_SCYTHE.get();
    }

    @Override
    public boolean hasChainsBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.JAILORS_SCYTHE.get();
    }

    public Rarity getRarity(ItemStack itemStack){

        if(this.unique){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 2;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 0;
    }
}
