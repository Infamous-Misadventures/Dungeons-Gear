package com.infamous.dungeons_gear.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AbstractMinecartContainer.class)
public abstract class ContainerMinecartEntityMixin extends AbstractMinecart implements Container, MenuProvider {

    @Shadow
    @Nullable
    private ResourceLocation lootTable;

    @Shadow
    private NonNullList<ItemStack> itemStacks;

    @Shadow
    private long lootTableSeed;

    private boolean isApplyingModifier = false;

    protected ContainerMinecartEntityMixin(EntityType<?> typeIn, Level world) {
        super(typeIn, world);
    }

    @Inject(at = @At("HEAD"), method = "unpackLootTable", cancellable = true)
    private void addLoot(@Nullable Player player, CallbackInfo callbackInfo){
        if (this.lootTable != null && this.level.getServer() != null) {
            LootTable lootTable = this.level.getServer().getLootTables().get(this.lootTable);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)player, this.lootTable);
            }

            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, this.position()).withOptionalRandomSeed(this.lootTableSeed);
            // Forge: add this entity to loot context, however, currently Vanilla uses 'this' for the player creating the chests. So we take over 'killer_entity' for this.
            lootcontext$builder.withParameter(LootContextParams.KILLER_ENTITY, this);
            if (player != null) {
                lootcontext$builder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
            }

            this.isApplyingModifier = true;
            lootTable.fill(this, lootcontext$builder.create(LootContextParamSets.CHEST));
            this.isApplyingModifier = false;

            // Moved to the bottom of the method instead of being in the middle, with an if-check to see if it was already set to null during the loot modifier's doApply call
            this.lootTable = null;
        }
        callbackInfo.cancel();
    }

    @Inject(at = @At("HEAD"), method = "getItem", cancellable = true)
    private void getStackInSlot(int index, CallbackInfoReturnable<ItemStack> cir){
        if(this.isApplyingModifier){
            cir.setReturnValue(this.itemStacks.get(index));
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "setItem", cancellable = true)
    private void setInventorySlotContents(int index, ItemStack stack, CallbackInfo callbackInfo){
        if(this.isApplyingModifier){
            this.itemStacks.set(index, stack);
            if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }
            callbackInfo.cancel();
        }
    }

}
