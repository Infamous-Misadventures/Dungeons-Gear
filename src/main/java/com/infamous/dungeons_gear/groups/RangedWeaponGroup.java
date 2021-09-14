package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RangedWeaponGroup extends ItemGroup
{
    public RangedWeaponGroup()
    {
        super("ranged_weapons");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemRegistry.AUTO_CROSSBOW.get());
    }
}
