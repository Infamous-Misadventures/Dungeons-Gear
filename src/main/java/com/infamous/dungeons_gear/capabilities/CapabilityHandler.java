package com.infamous.dungeons_gear.capabilities;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageProvider;
import com.infamous.dungeons_gear.capabilities.bow.BowProvider;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_libraries.DungeonsLibraries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class CapabilityHandler {
    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(DungeonsGear.MODID, "combo"), new ComboProvider());
            event.addCapability(new ResourceLocation(DungeonsLibraries.MODID, "artifact_usage"), new ArtifactUsageProvider());
        }
    }

    @SubscribeEvent
    public static void onAttachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ShootableItem)
            event.addCapability(new ResourceLocation(DungeonsGear.MODID, "bow"), new BowProvider());
        if (event.getObject().getItem() instanceof IDualWieldWeapon)
            event.addCapability(new ResourceLocation(DungeonsGear.MODID, "dualwield"), new OffhandProvider());
    }
}
