package com.infamous.dungeons_gear.capabilities.summoning;

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
}
