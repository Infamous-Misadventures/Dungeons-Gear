package com.infamous.dungeons_gear.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ContainerMinecartEntity.class)
public abstract class ContainerMinecartEntityMixin extends AbstractMinecartEntity implements IInventory, INamedContainerProvider {

    @Shadow
    @Nullable
    private ResourceLocation lootTable;

    @Shadow
    private NonNullList<ItemStack> minecartContainerItems;

    @Shadow
    private long lootTableSeed;

    private boolean isApplyingModifier = false;

    protected ContainerMinecartEntityMixin(EntityType<?> typeIn, World world) {
        super(typeIn, world);
    }

    @Inject(at = @At("HEAD"), method = "addLoot", cancellable = true)
    private void addLoot(@Nullable PlayerEntity player, CallbackInfo callbackInfo){
        if (this.lootTable != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootTableManager().getLootTableFromLocation(this.lootTable);
            if (player instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLAYER_GENERATES_CONTAINER_LOOT.test((ServerPlayerEntity)player, this.lootTable);
            }

            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.world)).withParameter(LootParameters.POSITION, this.getPosition()).withSeed(this.lootTableSeed);
            // Forge: add this entity to loot context, however, currently Vanilla uses 'this' for the player creating the chests. So we take over 'killer_entity' for this.
            lootcontext$builder.withParameter(LootParameters.KILLER_ENTITY, this);
            if (player != null) {
                lootcontext$builder.withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY, player);
            }

            this.isApplyingModifier = true;
            lootTable.fillInventory(this, lootcontext$builder.build(LootParameterSets.CHEST));
            this.isApplyingModifier = false;

            // Moved to the bottom of the method instead of being in the middle, with an if-check to see if it was already set to null during the loot modifier's doApply call
            this.lootTable = null;
        }
        callbackInfo.cancel();
    }

    @Inject(at = @At("HEAD"), method = "getStackInSlot", cancellable = true)
    private void getStackInSlot(int index, CallbackInfoReturnable<ItemStack> cir){
        if(this.isApplyingModifier){
            cir.setReturnValue(this.minecartContainerItems.get(index));
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "setInventorySlotContents", cancellable = true)
    private void setInventorySlotContents(int index, ItemStack stack, CallbackInfo callbackInfo){
        if(this.isApplyingModifier){
            this.minecartContainerItems.set(index, stack);
            if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
                stack.setCount(this.getInventoryStackLimit());
            }
            callbackInfo.cancel();
        }
    }

}
