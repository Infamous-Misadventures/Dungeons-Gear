package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ISummoner {

    void copyFrom(ISummoner summoner);

    void setSummonedGolem(UUID golem);
    void setSummonedWolf(UUID wolf);
    void setSummonedLlama(UUID llama);
    void setSummonedBat(UUID bat);
    void setSummonedSheep(UUID enchantedSheep);

    UUID getSummonedGolem();
    UUID getSummonedWolf();
    UUID getSummonedLlama();
    UUID getSummonedBat();
    UUID getSummonedSheep();

    boolean addBuzzyNestBee(UUID buzzyNestBee);
    boolean addTumblebeeBee(UUID tumblebeeBee);
    boolean addBusyBeeBee(UUID busyBeeBee);

    UUID[] getBuzzyNestBees();
    UUID[] getTumblebeeBees();
    UUID[] getBusyBeeBees();

    boolean hasNoBuzzyNestBees();

    default List<UUID> getSummonedMobs(){
        List<UUID> summonedMobs = NonNullList.create();
        if(this.getSummonedBat() != null){
            summonedMobs.add(this.getSummonedBat());
        }
        if(this.getSummonedGolem() != null){
            summonedMobs.add(this.getSummonedGolem());
        }
        if(this.getSummonedLlama() != null){
            summonedMobs.add(this.getSummonedLlama());
        }
        if(this.getSummonedSheep() != null){
            summonedMobs.add(this.getSummonedSheep());
        }
        if(this.getSummonedWolf() != null){
            summonedMobs.add(this.getSummonedWolf());
        }
        for(UUID busyBee : this.getBusyBeeBees()){
            if(busyBee != null){
                summonedMobs.add(busyBee);
            }
        }
        for(UUID buzzyNestBee : this.getBuzzyNestBees()){
            if(buzzyNestBee != null){
                summonedMobs.add(buzzyNestBee);
            }
        }
        for(UUID tumblebeeBee : this.getTumblebeeBees()){
            if(tumblebeeBee != null){
                summonedMobs.add(tumblebeeBee);
            }
        }
        return summonedMobs;
    }
}
