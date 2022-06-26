package com.infamous.dungeons_gear.capabilities.combo;

import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import static com.infamous.dungeons_gear.registry.AttributeRegistry.ROLL_COOLDOWN;
import static com.infamous.dungeons_gear.registry.AttributeRegistry.ROLL_LIMIT;

public class RollHelper {
    public static void incrementJumpCounter(LivingEntity livingEntity){
        ICombo comboCap = CapabilityHelper.getComboCapability(livingEntity);
        if(comboCap == null) return;

        comboCap.setJumpCounter(comboCap.getJumpCounter() + 1);
    }

    public static int getRollLimit(LivingEntity livingEntity){
        ModifiableAttributeInstance attribute = livingEntity.getAttribute(ROLL_LIMIT.get());
        return attribute != null ? (int) attribute.getValue() : 1;
    }


    public static boolean hasReachedJumpLimit(LivingEntity jumper){
        ICombo comboCap = CapabilityHelper.getComboCapability(jumper);
        if(comboCap == null) return false;

        ModifiableAttributeInstance attribute = jumper.getAttribute(ROLL_LIMIT.get());
        int jumpLimit = attribute != null ? (int) attribute.getValue() : 1;
        return comboCap.getJumpCounter() >= jumpLimit;
    }

    public static void startCooldown(LivingEntity jumper, ICombo comboCap) {
        ModifiableAttributeInstance attribute = jumper.getAttribute(ROLL_COOLDOWN.get());
        int jumpCooldownTimerLength = attribute != null ? (int) attribute.getValue() : 180;
        comboCap.setJumpCooldownTimer(jumpCooldownTimerLength);
    }
}
