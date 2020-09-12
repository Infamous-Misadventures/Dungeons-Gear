package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public interface ISummonable {

    UUID getSummoner();

    void setSummoner(UUID summoner);

}
