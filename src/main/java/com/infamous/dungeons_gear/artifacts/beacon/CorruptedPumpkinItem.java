package com.infamous.dungeons_gear.artifacts.beacon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class CorruptedPumpkinItem extends AbstractBeaconItem {

    public static final BeaconBeamColor CORRUPTED_PUMPKIN_BEAM_COLOR =
            new BeaconBeamColor((short) 255, (short) 165, (short) 0, (short) 255, (short) 255, (short) 255);

    public CorruptedPumpkinItem(Properties properties) {
        super(properties);
    }

    @Override
    public BeaconBeamColor getBeamColor() {
        return CORRUPTED_PUMPKIN_BEAM_COLOR;
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Corrupted Pumpkin glows with supernatural power, illuminating even the darkest nights with its powerful beacon."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Fires a high-powered beam that continuously damages mobs."));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}
