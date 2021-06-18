package com.infamous.dungeons_gear.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBreakItem {
    private final ItemStack stack;
    private final int entityID;

    public PacketBreakItem(int entityID, ItemStack stack) {
        this.stack = stack;
        this.entityID = entityID;
    }

    public static void encode(PacketBreakItem packet, PacketBuffer buf) {
        buf.writeInt(packet.entityID);
        buf.writeItemStack(packet.stack);
    }

    public static PacketBreakItem decode(PacketBuffer buf) {
        return new PacketBreakItem(buf.readInt(), buf.readItemStack());
    }

    public static class BreakItemHandler {
        public static void handle(PacketBreakItem packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable() {

                    private static final long serialVersionUID = 1;

                    @Override
                    public void run() {
                        ClientWorld world = Minecraft.getInstance().world;
                        Entity target = null;
                        if (world != null)
                            target = world.getEntityByID(packet.entityID);
                        if (target instanceof LivingEntity) {
                            ((LivingEntity) target).renderBrokenItemStack(packet.stack);
                        }
                    }

                }));
            }
        }
    }
}
