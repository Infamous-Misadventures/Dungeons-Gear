package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SummonerStorage implements Capability.IStorage<ISummoner> {

    @Override
    public INBT writeNBT(Capability<ISummoner> capability, ISummoner instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        if(instance.getSummonedGolem() != null){
            tag.putUniqueId("golem", instance.getSummonedGolem());
        }
        if(instance.getSummonedWolf() != null){
            tag.putUniqueId("wolf", instance.getSummonedWolf());
        }
        if(instance.getSummonedLlama() != null){
            tag.putUniqueId("llama", instance.getSummonedLlama());
        }
        if(instance.getSummonedBat() != null){
            tag.putUniqueId("bat", instance.getSummonedBat());
        }
        return tag;
    }

    @Override
    public void readNBT(Capability<ISummoner> capability, ISummoner instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        if(tag.hasUniqueId("golem")){
            instance.setSummonedGolem(tag.getUniqueId("golem"));
        }
        if(tag.hasUniqueId("wolf")){
            instance.setSummonedWolf(tag.getUniqueId("wolf"));
        }
        if(tag.hasUniqueId("llama")){
            instance.setSummonedLlama(tag.getUniqueId("llama"));
        }
        if(tag.hasUniqueId("bat")){
            instance.setSummonedBat(tag.getUniqueId("bat"));
        }
    }
}
