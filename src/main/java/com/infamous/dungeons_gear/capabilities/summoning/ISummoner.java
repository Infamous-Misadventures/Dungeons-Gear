package com.infamous.dungeons_gear.capabilities.summoning;

import java.util.List;
import java.util.UUID;

public interface ISummoner {

    void setSummonedGolem(UUID golem);
    void setSummonedWolf(UUID wolf);
    void setSummonedLlama(UUID llama);
    void setSummonedBat(UUID bat);

    UUID getSummonedGolem();
    UUID getSummonedWolf();
    UUID getSummonedLlama();
    UUID getSummonedBat();

    UUID[] getBuzzyNestBees();

    boolean addBuzzyNestBee(UUID buzzyNestBee);
    boolean addTumblebeeBee(UUID tumblebeeBee);
    boolean addBusyBeeBee(UUID busyBeeBee);

    UUID[] getTumblebeeBees();
    UUID[] getBusyBeeBees();
}
