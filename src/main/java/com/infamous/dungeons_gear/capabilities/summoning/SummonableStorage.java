package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SummonableStorage implements Capability.IStorage<ISummonable> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ISummonable> capability, ISummonable instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        if(instance.getSummoner() != null){
            tag.putUniqueId("summoner", instance.getSummoner());
        }
        return tag;
    }

    @Override
    public void readNBT(Capability<ISummonable> capability, ISummonable instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        if(tag.hasUniqueId("summoner")){
            instance.setSummoner(tag.getUniqueId("summoner"));
        }
    }
}
