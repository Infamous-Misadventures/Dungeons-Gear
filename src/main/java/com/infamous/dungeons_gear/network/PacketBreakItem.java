package com.infamous.dungeons_gear.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBreakItem {
    private final ItemStack stack;
    private final int entityID;

    public PacketBreakItem(int entityID, ItemStack stack) {
        this.stack = stack;
        this.entityID = entityID;
    }

    public static void encode(PacketBreakItem packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityID);
        buf.writeItem(packet.stack);
    }

    public static PacketBreakItem decode(FriendlyByteBuf buf) {
        return new PacketBreakItem(buf.readInt(), buf.readItem());
    }

    public static class BreakItemHandler {
        public static void handle(PacketBreakItem packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable() {

                    private static final long serialVersionUID = 1;

                    @Override
                    public void run() {
                        ClientLevel world = Minecraft.getInstance().level;
                        Entity target = null;
                        if (world != null)
                            target = world.getEntity(packet.entityID);
                        if (target instanceof LivingEntity) {
                            ((LivingEntity) target).breakItem(packet.stack);
                        }
                    }

                }));
            }
        }
    }
}
