package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import java.util.UUID;

public class SummonerStorage implements Capability.IStorage<ISummoner> {

    @Override
    public INBT writeNBT(Capability<ISummoner> capability, ISummoner instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        if(instance.getSummonedGolem() != null){
            tag.putUUID("golem", instance.getSummonedGolem());
        }
        if(instance.getSummonedWolf() != null){
            tag.putUUID("wolf", instance.getSummonedWolf());
        }
        if(instance.getSummonedLlama() != null){
            tag.putUUID("llama", instance.getSummonedLlama());
        }
        if(instance.getSummonedBat() != null){
            tag.putUUID("bat", instance.getSummonedBat());
        }
        if(instance.getSummonedSheep() != null){
            tag.putUUID("sheep", instance.getSummonedSheep());
        }
        for(int i = 0; i < 3; i++){
            UUID buzzyNestBee = instance.getBuzzyNestBees()[i];
            UUID tumblebeeBee = instance.getTumblebeeBees()[i];
            UUID busyBeeBee = instance.getBusyBeeBees()[i];
            if(buzzyNestBee != null){
                tag.putUUID("buzzyNestBee" + i, buzzyNestBee);
            }
            if(tumblebeeBee != null){
                tag.putUUID("tumblebeeBee" + i, tumblebeeBee);
            }
            if(busyBeeBee != null){
                tag.putUUID("busyBeeBee" + i, busyBeeBee);
            }
        }
        return tag;
    }

    @Override
    public void readNBT(Capability<ISummoner> capability, ISummoner instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        if(tag.hasUUID("golem")){
            instance.setSummonedGolem(tag.getUUID("golem"));
        }
        if(tag.hasUUID("wolf")){
            instance.setSummonedWolf(tag.getUUID("wolf"));
        }
        if(tag.hasUUID("llama")){
            instance.setSummonedLlama(tag.getUUID("llama"));
        }
        if(tag.hasUUID("bat")){
            instance.setSummonedBat(tag.getUUID("bat"));
        }
        if(tag.hasUUID("sheep")){
            instance.setSummonedSheep(tag.getUUID("sheep"));
        }
        for(int i = 0; i < 3; i++){
            String currentBuzzyNestBee = "buzzyNestBee" + i;
            if(tag.hasUUID(currentBuzzyNestBee)){
                instance.addBuzzyNestBee(tag.getUUID(currentBuzzyNestBee));
            }
            String currentTumblebeeBee = "tumblebeeBee" + i;
            if(tag.hasUUID(currentTumblebeeBee)){
                instance.addTumblebeeBee(tag.getUUID(currentTumblebeeBee));
            }
            String currentBusyBeeBee = "busyBeeBee" + i;
            if(tag.hasUUID(currentBusyBeeBee)){
                instance.addBusyBeeBee(tag.getUUID(currentBusyBeeBee));
            }
        }
    }
}
