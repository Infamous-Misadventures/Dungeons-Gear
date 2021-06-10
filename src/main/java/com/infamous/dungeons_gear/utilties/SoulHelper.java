package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.combat.PacketUpdateSouls;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class SoulHelper {
    public static float getMaxSoulsCollectable(LivingEntity elb) {
        return 300;//+EnchantmentHelper.getMaxEnchantmentLevel(, player)*100;
    }

    public static void addSoul(LivingEntity le, float amount) {
        if (CapabilityHelper.getComboCapability(le) == null) return;
        CapabilityHelper.getComboCapability(le).addSouls(amount, SoulHelper.getMaxSoulsCollectable(le));
        if (le instanceof ServerPlayerEntity) {
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) le), new PacketUpdateSouls(CapabilityHelper.getComboCapability(le).getSouls()));
        }
    }
}
