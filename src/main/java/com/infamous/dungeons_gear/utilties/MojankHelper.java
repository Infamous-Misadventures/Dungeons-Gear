package com.infamous.dungeons_gear.utilties;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;

public class MojankHelper {

    /** Lambda implementation, as done in SwordItem, was causing some weird remap of the hurtEnemy methods that reimplemented that lambda.
     * Moving lambda into a static helper method fixed the issue.
     * Try undoing in 1.18
     * */
    public static void hurtEnemyBroadcastBreakEvent(LivingEntity livingEntity) {
        livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    }
}
