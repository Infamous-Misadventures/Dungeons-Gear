package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MeleeWeaponGroup extends ItemGroup
{
    public MeleeWeaponGroup()
    {
        super("melee_weapons");
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ItemRegistry.BROADSWORD.get());
    }
}
