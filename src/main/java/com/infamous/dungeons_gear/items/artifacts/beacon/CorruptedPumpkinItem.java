package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

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
}
