package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RangedWeaponGroup extends CreativeModeTab
{
    public RangedWeaponGroup()
    {
        super("ranged_weapons");
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ItemRegistry.AUTO_CROSSBOW.get());
    }
}
