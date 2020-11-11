package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.init.DeferredItemInit;
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
        return new ItemStack(DeferredItemInit.TOTEM_OF_REGENERATION.get());
    }
}
