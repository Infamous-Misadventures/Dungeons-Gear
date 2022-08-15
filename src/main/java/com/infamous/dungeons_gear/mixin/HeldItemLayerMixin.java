package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public class HeldItemLayerMixin<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> {
    private T entity;

    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"))
    public void dungeons_renderGetEntity(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_, CallbackInfo ci){
        entity = p_225628_4_;
    }

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("STORE"),
            ordinal = 0)
    private ItemStack dungeons_renderOverwriteFirst(ItemStack original) {
        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(entity);
        if(cap != null && cap.isUsingArtifact()){
            return cap.getUsingArtifact();
        }
        return original;
    }

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("STORE"),
            ordinal = 1)
    private ItemStack dungeons_renderOverwriteSecond(ItemStack original) {
        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(entity);
        if(cap != null && cap.isUsingArtifact()){
            return ItemStack.EMPTY;
        }
        return original;
    }
}
