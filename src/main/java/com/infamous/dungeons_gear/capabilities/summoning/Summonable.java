package com.infamous.dungeons_gear.capabilities.summoning;

import javax.annotation.Nullable;
import java.util.UUID;

public class Summonable implements ISummonable {

    @Nullable
    private UUID summoner;

    public Summonable(){
    }

    @Override
    @Nullable
    public UUID getSummoner() {
        return this.summoner;
    }
    @Override
    public void setSummoner(
            @Nullable UUID summoner) {
        this.summoner = summoner;
    }
}
