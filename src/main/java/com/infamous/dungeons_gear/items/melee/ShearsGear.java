package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_libraries.items.gearconfig.MeleeGear;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearsGear extends MeleeGear {

    public ShearsGear(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mineBlock(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
        if (!p_179218_2_.isClientSide && !p_179218_3_.getBlock().is(BlockTags.FIRE)) {
            p_179218_1_.hurtAndBreak(1, p_179218_5_, (p_220036_0_) -> {
                p_220036_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return !p_179218_3_.is(BlockTags.LEAVES) && !p_179218_3_.is(Blocks.COBWEB) && !p_179218_3_.is(Blocks.GRASS) && !p_179218_3_.is(Blocks.FERN) && !p_179218_3_.is(Blocks.DEAD_BUSH) && !p_179218_3_.is(Blocks.VINE) && !p_179218_3_.is(Blocks.TRIPWIRE) && !p_179218_3_.is(BlockTags.WOOL) ? super.mineBlock(p_179218_1_, p_179218_2_, p_179218_3_, p_179218_4_, p_179218_5_) : true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        return p_150897_1_.is(Blocks.COBWEB) || p_150897_1_.is(Blocks.REDSTONE_WIRE) || p_150897_1_.is(Blocks.TRIPWIRE);
    }

    @Override
    public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
        if (!p_150893_2_.is(Blocks.COBWEB) && !p_150893_2_.is(BlockTags.LEAVES)) {
            return p_150893_2_.is(BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(p_150893_1_, p_150893_2_);
        } else {
            return 15.0F;
        }
    }

    @Override
    public net.minecraft.util.ActionResultType interactLivingEntity(ItemStack stack, net.minecraft.entity.player.PlayerEntity playerIn, LivingEntity entity, net.minecraft.util.Hand hand) {
        if (entity.level.isClientSide) return net.minecraft.util.ActionResultType.PASS;
        if (entity instanceof net.minecraftforge.common.IForgeShearable) {
            net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
            BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
            if (target.isShearable(stack, entity.level, pos)) {
                java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.level, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.BLOCK_FORTUNE, stack));
                java.util.Random rand = new java.util.Random();
                drops.forEach(d -> {
                    net.minecraft.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                    ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                });
                stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(hand));
            }
            return net.minecraft.util.ActionResultType.SUCCESS;
        }
        return net.minecraft.util.ActionResultType.PASS;
    }
}
