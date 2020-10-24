package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.UUID;

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
        for(int i = 0; i < 3; i++){
            UUID buzzyNestBee = instance.getBuzzyNestBees()[i];
            UUID tumblebeeBee = instance.getTumblebeeBees()[i];
            UUID busyBeeBee = instance.getBusyBeeBees()[i];
            if(buzzyNestBee != null){
                tag.putUniqueId("buzzyNestBee" + i, buzzyNestBee);
            }
            if(tumblebeeBee != null){
                tag.putUniqueId("tumblebeeBee" + i, tumblebeeBee);
            }
            if(busyBeeBee != null){
                tag.putUniqueId("busyBeeBee" + i, busyBeeBee);
            }
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
        for(int i = 0; i < 3; i++){
            String currentBuzzyNestBee = "buzzyNestBee" + i;
            if(tag.hasUniqueId(currentBuzzyNestBee)){
                instance.addBuzzyNestBee(tag.getUniqueId(currentBuzzyNestBee));
            }
            String currentTumblebeeBee = "tumblebeeBee" + i;
            if(tag.hasUniqueId(currentTumblebeeBee)){
                instance.addTumblebeeBee(tag.getUniqueId(currentTumblebeeBee));
            }
            String currentBusyBeeBee = "busyBeeBee" + i;
            if(tag.hasUniqueId(currentBusyBeeBee)){
                instance.addBusyBeeBee(tag.getUniqueId(currentBusyBeeBee));
            }
        }
    }
}
