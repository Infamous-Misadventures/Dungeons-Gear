package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.items.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ArtifactGroup extends ItemGroup
{
    public ArtifactGroup()
    {
        super("artifacts");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemRegistry.TOTEM_OF_REGENERATION.get());
    }
}
