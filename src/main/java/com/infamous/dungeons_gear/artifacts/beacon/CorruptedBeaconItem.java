package com.infamous.dungeons_gear.artifacts.beacon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
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

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Corrupted Beacon holds immense power within. It waits for the moment to unleash its wrath."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Fires a high-powered beam that continuously damages mobs."));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
    }
}
