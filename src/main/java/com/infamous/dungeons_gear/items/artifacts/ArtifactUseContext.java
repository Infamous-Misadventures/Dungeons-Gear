package com.infamous.dungeons_gear.items.artifacts;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ArtifactUseContext {
   @Nullable
   private final Player player;
   private final BlockHitResult hitResult;
   private final Level level;
   private final ItemStack itemStack;

   public ArtifactUseContext(Player p_i50033_1_, InteractionHand p_i50033_2_, BlockHitResult p_i50033_3_) {
      this(p_i50033_1_.level, p_i50033_1_, p_i50033_1_.getItemInHand(p_i50033_2_), p_i50033_3_);
   }

   public ArtifactUseContext(Level p_i50034_1_, @Nullable Player p_i50034_2_, ItemStack p_i50034_4_, BlockHitResult p_i50034_5_) {
      this.player = p_i50034_2_;
      this.hitResult = p_i50034_5_;
      this.itemStack = p_i50034_4_;
      this.level = p_i50034_1_;
   }

   protected final BlockHitResult getHitResult() {
      return this.hitResult;
   }

   public BlockPos getClickedPos() {
      return this.hitResult.getBlockPos();
   }

   public Direction getClickedFace() {
      return this.hitResult.getDirection();
   }

   public Vec3 getClickLocation() {
      return this.hitResult.getLocation();
   }

   public boolean isInside() {
      return this.hitResult.isInside();
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   @Nullable
   public Player getPlayer() {
      return this.player;
   }

   public Level getLevel() {
      return this.level;
   }

   public Direction getHorizontalDirection() {
      return this.player == null ? Direction.NORTH : this.player.getDirection();
   }

   public boolean isSecondaryUseActive() {
      return this.player != null && this.player.isSecondaryUseActive();
   }

   public float getRotation() {
      return this.player == null ? 0.0F : this.player.getYRot();
   }
}
