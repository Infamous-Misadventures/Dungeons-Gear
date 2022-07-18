package com.infamous.dungeons_gear.items.melee;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ShearsGear extends DualWieldMeleeGear {

    public ShearsGear(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mineBlock(ItemStack p_179218_1_, Level p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
        if (!p_179218_2_.isClientSide && !ForgeRegistries.BLOCKS.tags().getTag(BlockTags.FIRE).contains(p_179218_3_.getBlock())) {
            p_179218_1_.hurtAndBreak(1, p_179218_5_, (p_220036_0_) -> {
                p_220036_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public net.minecraft.world.InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity.level.isClientSide) return net.minecraft.world.InteractionResult.PASS;
        if (entity instanceof net.minecraftforge.common.IForgeShearable) {
            net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
            BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
            if (target.isShearable(stack, entity.level, pos)) {
                java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.level, pos,
                        net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.BLOCK_FORTUNE, stack));
                java.util.Random rand = new java.util.Random();
                drops.forEach(d -> {
                    net.minecraft.world.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                    ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                });
                stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(hand));
            }
            return net.minecraft.world.InteractionResult.SUCCESS;
        }
        return net.minecraft.world.InteractionResult.PASS;
    }
}
