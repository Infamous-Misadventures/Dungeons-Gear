package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public abstract class SoulBeaconItem extends AbstractBeaconItem implements ISoulGatherer {

    public SoulBeaconItem(Properties properties) {
        super(properties);
    }

    public boolean canFire(PlayerEntity playerEntity, ItemStack stack) {
        ISoulGatherer soulGatherer = stack.getItem() instanceof ISoulGatherer ? ((ISoulGatherer) stack.getItem()) : null;
        if (soulGatherer != null) {
            return CapabilityHelper.getComboCapability(playerEntity).getSouls() >= soulGatherer.getActivationCost(stack) || playerEntity.isCreative();
        }
        return false;
    }

    @Override
    protected boolean consumeTick(PlayerEntity playerEntity) {
        ICombo comboCap = CapabilityHelper.getComboCapability(playerEntity);
        if(comboCap == null) return false;
        return comboCap.consumeSouls(SOUL_COST_PER_TICK);
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return AbstractBeaconItem.SOUL_COST_PER_TICK;
    }
}
