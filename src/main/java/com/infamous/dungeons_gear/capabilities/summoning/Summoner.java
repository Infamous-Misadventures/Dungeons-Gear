package com.infamous.dungeons_gear.capabilities.summoning;

import javax.annotation.Nullable;
import java.util.UUID;

public class Summoner implements ISummoner {

    @Nullable
    private UUID summonedGolem;
    @Nullable
    private UUID summonedWolf;
    @Nullable
    private UUID summonedLlama;
    @Nullable
    private UUID summonedBat;
    @Nullable
    private UUID summonedSheep;

    private UUID[] buzzyNestBees = new UUID[3];
    private UUID[] busyBeeBees = new UUID[3];
    private UUID[] tumblebeeBees = new UUID[3];

    public Summoner(){
        //this.summonedGolem = UUID.randomUUID();
        //this.summonedWolf = UUID.randomUUID();
        //this.summonedLlama = UUID.randomUUID();
        //this.summonedBat = UUID.randomUUID();
    }

    @Override
    public void copyFrom(ISummoner summoner) {
        this.setSummonedBat(summoner.getSummonedBat());
        this.setSummonedGolem(summoner.getSummonedGolem());
        this.setSummonedLlama(summoner.getSummonedLlama());
        this.setSummonedSheep(summoner.getSummonedSheep());
        this.setSummonedWolf(summoner.getSummonedBat());
    }

    @Override
    public void setSummonedGolem(@Nullable UUID golem) {
        this.summonedGolem = golem;
    }

    @Override
    public void setSummonedWolf(@Nullable UUID wolf) {
        this.summonedWolf = wolf;
    }

    @Override
    public void setSummonedLlama(@Nullable UUID llama) {
        this.summonedLlama = llama;
    }

    @Override
    public void setSummonedBat(@Nullable UUID bat) {
        this.summonedBat = bat;
    }

    @Override
    public void setSummonedSheep(UUID enchantedSheep) {
        this.summonedSheep = enchantedSheep;
    }

    @Override
    @Nullable
    public UUID getSummonedGolem() {
        return this.summonedGolem;
    }

    @Override
    @Nullable
    public UUID getSummonedWolf() {
        return this.summonedWolf;
    }

    @Override
    @Nullable
    public UUID getSummonedLlama() {
        return this.summonedLlama;
    }

    @Override
    @Nullable
    public UUID getSummonedBat() {
        return this.summonedBat;
    }

    @Override
    public UUID getSummonedSheep() {
        return this.summonedSheep;
    }

    @Override
    public UUID[] getBuzzyNestBees() {
        return this.buzzyNestBees;
    }

    @Override
    public boolean addBuzzyNestBee(UUID buzzyNestBee){
        if(this.buzzyNestBees[0] == null){
            this.buzzyNestBees[0] = buzzyNestBee;
            return true;
        }
        else if(this.buzzyNestBees[1] == null){
            this.buzzyNestBees[1] = buzzyNestBee;
            return true;
        }
        else if(this.buzzyNestBees[2] == null){
            this.buzzyNestBees[2] = buzzyNestBee;
            return true;
        }
        return false;
    }

    @Override
    public UUID[] getTumblebeeBees() {
        return this.tumblebeeBees;
    }

    @Override
    public boolean addTumblebeeBee(UUID tumblebeeBee) {
        if(this.tumblebeeBees[0] == null){
            this.tumblebeeBees[0] = tumblebeeBee;
            return true;
        }
        else if(this.tumblebeeBees[1] == null){
            this.tumblebeeBees[1] = tumblebeeBee;
            return true;
        }
        else if(this.tumblebeeBees[2] == null){
            this.tumblebeeBees[2] = tumblebeeBee;
            return true;
        }
        return false;
    }

    @Override
    public UUID[] getBusyBeeBees() {
        return this.busyBeeBees;
    }

    @Override
    public boolean hasNoBuzzyNestBees() {
        return this.buzzyNestBees[0] == null && this.buzzyNestBees[1] == null && this.buzzyNestBees[2] == null;
    }

    @Override
    public boolean addBusyBeeBee(UUID busyBeeBee) {
        if(this.busyBeeBees[0] == null){
            this.busyBeeBees[0] = busyBeeBee;
            return true;
        }
        else if(this.busyBeeBees[1] == null){
            this.busyBeeBees[1] = busyBeeBee;
            return true;
        }
        else if(this.busyBeeBees[2] == null){
            this.busyBeeBees[2] = busyBeeBee;
            return true;
        }
        return false;
    }
}
