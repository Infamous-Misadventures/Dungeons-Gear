package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RangedWeaponGroup extends CreativeModeTab {
    public RangedWeaponGroup() {
        super("ranged_weapons");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.AUTO_CROSSBOW.get());
    }
}
