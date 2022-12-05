package com.infamous.dungeons_gear.items.artifacts.beacon;

import net.minecraft.world.item.Item.Properties;

public class CorruptedBeaconItem extends SoulBeaconItem {

    public static final BeamColor CORRUPTED_BEACON_BEAM_COLOR =
            new BeamColor((short) 90, (short) 0, (short) 90, (short) 255, (short) 255, (short) 255);

    public CorruptedBeaconItem(Properties properties) {
        super(properties);
    }

    @Override
    public BeamColor getBeamColor() {
        return CORRUPTED_BEACON_BEAM_COLOR;
    }
}
