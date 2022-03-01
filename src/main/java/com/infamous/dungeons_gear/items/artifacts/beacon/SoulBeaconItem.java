package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class SoulBeaconItem extends AbstractBeaconItem implements ISoulConsumer {

    public SoulBeaconItem(Properties properties) {
        super(properties);
    }

    public boolean canFire(PlayerEntity playerEntity, ItemStack stack) {
        return SoulCasterHelper.canConsumeSouls(playerEntity, stack);
    }

    @Override
    protected boolean consumeTick(PlayerEntity playerEntity, ItemStack stack) {
        return SoulCasterHelper.consumeSouls(playerEntity, stack);
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return AbstractBeaconItem.SOUL_COST_PER_TICK;
    }
}
