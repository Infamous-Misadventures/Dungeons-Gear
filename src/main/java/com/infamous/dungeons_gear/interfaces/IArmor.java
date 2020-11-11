package com.infamous.dungeons_gear.interfaces;

public interface IArmor {
    default boolean doHealthPotionsHealNearbyAllies(){
        return false;
    }
    default boolean doGivesYouAPetBat(){
        return false;
    }
    default boolean doBriefInvulnerabilityWhenJumping(){
        return false;
    }

    default double getMagicDamage(){
        return 0;
    }
    default double getRangedDamage(){
        return 0;
    }
    default double getArtifactCooldown(){
        return 0;
    }
    default double getHealthPotionBoost(){
        return 0;
    }
    default double getSoulsGathered(){
        return 0;
    }
    default double getLifeSteal(){
        return 0;
    }
    default int getArrowsPerBundle(){
        return 0;
    }
    default double getChanceToTeleportAwayWhenHit(){
        return 0;
    }
    default double getHigherJumps(){
        return 0;
    }
    default double getChanceToNegateHits(){
        return 0;
    }
    default double getLongerJumpAbilityCooldown(){
        return 0;
    }
    default double getFreezingResistance(){
        return 0;
    }
}
