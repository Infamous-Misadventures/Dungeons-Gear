package com.infamous.dungeons_gear.artifacts.beacon;

import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class CorruptedBeaconItem extends AbstractBeaconItem {

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
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

}
