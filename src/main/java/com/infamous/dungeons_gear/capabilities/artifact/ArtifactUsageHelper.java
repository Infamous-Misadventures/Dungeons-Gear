package com.infamous.dungeons_gear.capabilities.artifact;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.ARTIFACT_USAGE_CAPABILITY;


public class ArtifactUsageHelper {

    public static LazyOptional<ArtifactUsage> getArtifactUsageCapabilityLazy(Entity entity)
    {
        if(ARTIFACT_USAGE_CAPABILITY == null) {
            return LazyOptional.empty();
        }
        LazyOptional<ArtifactUsage> lazyCap = entity.getCapability(ARTIFACT_USAGE_CAPABILITY);
        return lazyCap;
    }

    public static ArtifactUsage getArtifactUsageCapability(Entity entity)
    {
        LazyOptional<ArtifactUsage> lazyCap = entity.getCapability(ARTIFACT_USAGE_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the Artifact Usage capability from the Entity!"));
        }
        return null;
    }

    public static boolean startUsingArtifact(Player playerIn, ArtifactUsage cap, ItemStack itemstack){
        boolean result = cap.startUsingArtifact(itemstack);
        return result;
    }
}
