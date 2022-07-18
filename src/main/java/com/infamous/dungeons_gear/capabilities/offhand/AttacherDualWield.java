package com.infamous.dungeons_gear.capabilities.offhand;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

import static com.infamous.dungeons_libraries.DungeonsLibraries.MODID;

public class AttacherDualWield {

    private static class DualWieldProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "dual_wield");
        private final DualWield backend = new DualWield();
        private final LazyOptional<DualWield> optionalData = LazyOptional.of(() -> backend);

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
            return ModCapabilities.DUAL_WIELD_CAPABILITY.orEmpty(cap, this.optionalData);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static void attach(final AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack itemStack = event.getObject();
        if (itemStack.getItem() instanceof IDualWieldWeapon) {
            final AttacherDualWield.DualWieldProvider provider = new AttacherDualWield.DualWieldProvider();
            event.addCapability(AttacherDualWield.DualWieldProvider.IDENTIFIER, provider);
        }
    }
}
