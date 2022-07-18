package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import static com.infamous.dungeons_gear.registry.AttributeRegistry.ROLL_COOLDOWN;
import static com.infamous.dungeons_gear.registry.AttributeRegistry.ROLL_LIMIT;

public class RollHelper {
    public static void incrementJumpCounter(LivingEntity livingEntity){
        Combo comboCap = ComboHelper.getComboCapability(livingEntity);
        if(comboCap == null) return;

        comboCap.setJumpCounter(comboCap.getJumpCounter() + 1);
    }

    public static int getRollLimit(LivingEntity livingEntity){
        AttributeInstance attribute = livingEntity.getAttribute(ROLL_LIMIT.get());
        return attribute != null ? (int) attribute.getValue() : 1;
    }


    public static boolean hasReachedJumpLimit(LivingEntity jumper){
        Combo comboCap = ComboHelper.getComboCapability(jumper);
        if(comboCap == null) return false;

        AttributeInstance attribute = jumper.getAttribute(ROLL_LIMIT.get());
        int jumpLimit = attribute != null ? (int) attribute.getValue() : 1;
        return comboCap.getJumpCounter() >= jumpLimit;
    }

    public static void startCooldown(LivingEntity jumper, Combo comboCap) {
        AttributeInstance attribute = jumper.getAttribute(ROLL_COOLDOWN.get());
        int jumpCooldownTimerLength = attribute != null ? (int) attribute.getValue() : 180;
        comboCap.setJumpCooldownTimer(jumpCooldownTimerLength);
    }
}
