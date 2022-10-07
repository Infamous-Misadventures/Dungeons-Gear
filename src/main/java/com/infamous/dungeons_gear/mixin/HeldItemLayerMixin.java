package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_libraries.capabilities.artifact.IArtifactUsage;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemLayer.class)
public class HeldItemLayerMixin<T extends LivingEntity, M extends EntityModel<T> & IHasArm> {
    private T entity;

    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"))
    public void dungeons_renderGetEntity(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_, CallbackInfo ci){
        entity = p_225628_4_;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At("STORE"),
            ordinal = 0)
    private ItemStack dungeons_renderOverwriteFirst(ItemStack original) {
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(entity);
        if(cap != null && cap.isUsingArtifact()){
            return cap.getUsingArtifact();
        }
        return original;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At("STORE"),
            ordinal = 1)
    private ItemStack dungeons_renderOverwriteSecond(ItemStack original) {
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(entity);
        if(cap != null && cap.isUsingArtifact()){
            return ItemStack.EMPTY;
        }
        return original;
    }
}
