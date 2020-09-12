package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.RangedWeaponList;
import com.infamous.dungeons_gear.items.WeaponList;
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
        return new ItemStack(RangedWeaponList.AUTO_CROSSBOW);
    }
}
