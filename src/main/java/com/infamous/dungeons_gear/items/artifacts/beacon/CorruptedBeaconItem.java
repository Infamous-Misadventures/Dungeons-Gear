package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
