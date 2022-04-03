package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CorruptedBeaconItem extends SoulBeaconItem {

    public static final BeaconBeamColor CORRUPTED_BEACON_BEAM_COLOR =
            new BeaconBeamColor((short) 128, (short) 0, (short) 128, (short) 255, (short) 255, (short) 255);

    public CorruptedBeaconItem(Properties properties) {
        super(properties);
    }

    @Override
    public BeaconBeamColor getBeamColor() {
        return CORRUPTED_BEACON_BEAM_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
