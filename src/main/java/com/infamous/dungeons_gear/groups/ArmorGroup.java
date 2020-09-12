package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.ArmorList;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ArmorGroup extends ItemGroup
{
    public ArmorGroup()
    {
        super("armor");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ArmorList.FULL_METAL_ARMOR);
    }
}
