package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CorruptedPumpkinItem extends SoulBeaconItem {

    public static final BeamColor CORRUPTED_PUMPKIN_BEAM_COLOR =
            new BeamColor((short) 255, (short) 165, (short) 0, (short) 255, (short) 255, (short) 255);

    public CorruptedPumpkinItem(Properties properties) {
        super(properties);
    }

    @Override
    public BeamColor getBeamColor() {
        return CORRUPTED_PUMPKIN_BEAM_COLOR;
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
