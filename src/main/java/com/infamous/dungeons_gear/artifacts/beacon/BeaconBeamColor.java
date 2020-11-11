package com.infamous.dungeons_gear.artifacts.beacon;

public class BeaconBeamColor {

    private short redValue;
    private short greenValue;
    private short blueValue;
    private short innerRedValue;
    private short innerGreenValue;
    private short innerBlueValue;

    public BeaconBeamColor(short redValue, short greenValue, short blueValue, short innerRedValue, short innerGreenValue, short innerBlueValue){
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
}
