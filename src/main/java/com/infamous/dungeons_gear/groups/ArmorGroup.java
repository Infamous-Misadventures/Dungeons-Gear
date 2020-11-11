package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.init.DeferredItemInit;
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
        return new ItemStack(DeferredItemInit.FULL_METAL_ARMOR.get());
    }
}
