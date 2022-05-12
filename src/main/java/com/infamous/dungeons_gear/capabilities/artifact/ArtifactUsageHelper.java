package com.infamous.dungeons_gear.capabilities.artifact;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageProvider.ARTIFACT_USAGE_CAPABILITY;


public class ArtifactUsageHelper {

    public static LazyOptional<IArtifactUsage> getArtifactUsageCapabilityLazy(Entity entity)
    {
        if(ARTIFACT_USAGE_CAPABILITY == null) {
            return LazyOptional.empty();
        }
        LazyOptional<IArtifactUsage> lazyCap = entity.getCapability(ARTIFACT_USAGE_CAPABILITY);
        return lazyCap;
    }

    public static IArtifactUsage getArtifactUsageCapability(Entity entity)
    {
        LazyOptional<IArtifactUsage> lazyCap = entity.getCapability(ARTIFACT_USAGE_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the Artifact Usage capability from the Entity!"));
        }
        return null;
    }

    public static boolean startUsingArtifact(PlayerEntity playerIn, IArtifactUsage cap, ItemStack itemstack){
        boolean result = cap.startUsingArtifact(itemstack);
        return result;
    }
}
