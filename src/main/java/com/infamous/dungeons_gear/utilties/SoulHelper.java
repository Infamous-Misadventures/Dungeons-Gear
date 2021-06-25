package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.combat.PacketUpdateSouls;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class SoulHelper {

    public static void addSouls(LivingEntity le, float amount) {
        ICombo comboCap = CapabilityHelper.getComboCapability(le);
        if (comboCap == null) return;

        float newAmount = comboCap.getSouls() + amount;
        comboCap.setSouls(newAmount, le);
        if (le instanceof ServerPlayerEntity) {
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) le), new PacketUpdateSouls(comboCap.getSouls()));
        }
    }
}
