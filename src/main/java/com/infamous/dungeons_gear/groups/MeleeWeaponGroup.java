package com.infamous.dungeons_gear.groups;

import com.infamous.dungeons_gear.registry.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MeleeWeaponGroup extends CreativeModeTab {
    public MeleeWeaponGroup() {
        super("melee_weapons");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.BROADSWORD.get());
    }
}
