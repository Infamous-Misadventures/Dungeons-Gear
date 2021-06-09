package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.capabilities.bow.BowProvider;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class CapabilityHelper {

    @Nullable
    public static ICombo getComboCapability(Entity entity)
    {
        LazyOptional<ICombo> lazyCap = entity.getCapability(ComboProvider.COMBO_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the combo capability from the Entity!"));
        }
        return null;
    }

    @Nullable
    public static ISummoner getSummonerCapability(Entity entity)
    {
        LazyOptional<ISummoner> lazyCap = entity.getCapability(SummonerProvider.SUMMONER_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the summoner capability from the Entity!"));
        }
        return null;
    }

    @Nullable
    public static ISummonable getSummonableCapability(Entity entity)
    {
        LazyOptional<ISummonable> lazyCap = entity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the summonable capability from the Entity!"));
        }
        return null;
    }

    @Nullable
    public static IBow getWeaponCapability(ItemStack stack)
    {
        LazyOptional<IBow> lazyCap = stack.getCapability(BowProvider.WEAPON_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the weapon capability from the ItemStack!"));
        }
        return null;
    }
}
