package com.infamous.dungeons_gear.items.artifacts.beacon;

import net.minecraft.nbt.CompoundTag;

public class BeamColor {

    private final short redValue;
    private final short greenValue;
    private final short blueValue;
    private final short innerRedValue;
    private final short innerGreenValue;
    private final short innerBlueValue;

    public BeamColor(short redValue, short greenValue, short blueValue, short innerRedValue, short innerGreenValue, short innerBlueValue){
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
        this.innerRedValue = innerRedValue;
        this.innerGreenValue = innerGreenValue;
        this.innerBlueValue = innerBlueValue;
    }

    public short getRedValue() {
        return this.redValue;
    }

    public short getBlueValue() {
        return this.blueValue;
    }

    public short getGreenValue() {
        return this.greenValue;
    }

    public short getInnerRedValue() {
        return this.innerRedValue;
    }

    public short getInnerGreenValue() {
        return this.innerGreenValue;
    }

    public short getInnerBlueValue() {
        return this.innerBlueValue;
    }

    public static BeamColor load(CompoundTag beamColor) {
        beamColor.getShort("RedValue");
        beamColor.getShort("RedValue");
        beamColor.getShort("GreenValue");
        beamColor.getShort("BlueValue");
        beamColor.getShort("InnerRedValue");
        beamColor.getShort("InnerGreenValue");
        beamColor.getShort("InnerBlueValue");
        return null;
    }

    public CompoundTag save(CompoundTag compoundNBT) {
        compoundNBT.putShort("RedValue", redValue);
        compoundNBT.putShort("GreenValue", greenValue);
        compoundNBT.putShort("BlueValue", blueValue);
        compoundNBT.putShort("InnerRedValue", innerRedValue);
        compoundNBT.putShort("InnerGreenValue", innerGreenValue);
        compoundNBT.putShort("InnerBlueValue", innerBlueValue);
        return compoundNBT;
    }
}
