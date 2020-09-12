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

    public Summoner(){
        //this.summonedGolem = UUID.randomUUID();
        //this.summonedWolf = UUID.randomUUID();
        //this.summonedLlama = UUID.randomUUID();
        //this.summonedBat = UUID.randomUUID();
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
}
