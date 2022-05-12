package com.infamous.dungeons_gear.items.artifacts;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ArtifactUseContext {
   @Nullable
   private final PlayerEntity player;
   private final Hand hand;
   private final BlockRayTraceResult hitResult;
   private final World level;
   private final ItemStack itemStack;

   public ArtifactUseContext(PlayerEntity p_i50033_1_, Hand p_i50033_2_, BlockRayTraceResult p_i50033_3_) {
      this(p_i50033_1_.level, p_i50033_1_, p_i50033_2_, p_i50033_1_.getItemInHand(p_i50033_2_), p_i50033_3_);
   }

   public ArtifactUseContext(World p_i50034_1_, @Nullable PlayerEntity p_i50034_2_, Hand p_i50034_3_, ItemStack p_i50034_4_, BlockRayTraceResult p_i50034_5_) {
      this.player = p_i50034_2_;
      this.hand = p_i50034_3_;
      this.hitResult = p_i50034_5_;
      this.itemStack = p_i50034_4_;
      this.level = p_i50034_1_;
   }

   protected final BlockRayTraceResult getHitResult() {
      return this.hitResult;
   }

   public BlockPos getClickedPos() {
      return this.hitResult.getBlockPos();
   }

   public Direction getClickedFace() {
      return this.hitResult.getDirection();
   }

   public Vector3d getClickLocation() {
      return this.hitResult.getLocation();
   }

   public boolean isInside() {
      return this.hitResult.isInside();
   }

   public ItemStack getItemInHand() {
      return this.itemStack;
   }

   @Nullable
   public PlayerEntity getPlayer() {
      return this.player;
   }

   public Hand getHand() {
      return this.hand;
   }

   public World getLevel() {
      return this.level;
   }

   public Direction getHorizontalDirection() {
      return this.player == null ? Direction.NORTH : this.player.getDirection();
   }

   public boolean isSecondaryUseActive() {
      return this.player != null && this.player.isSecondaryUseActive();
   }

   public float getRotation() {
      return this.player == null ? 0.0F : this.player.yRot;
   }
}
