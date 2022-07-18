package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ArtifactGroup extends CreativeModeTab
{
    public ArtifactGroup()
    {
        super("artifacts");
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ItemRegistry.TOTEM_OF_REGENERATION.get());
    }
}
