package com.infamous.dungeons_gear.capabilities;

import com.infamous.dungeons_gear.capabilities.bow.AttacherRangedAbilities;
import com.infamous.dungeons_gear.capabilities.bow.RangedAbilities;
import com.infamous.dungeons_gear.capabilities.combo.AttacherCombo;
import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.offhand.AttacherDualWield;
import com.infamous.dungeons_gear.capabilities.offhand.DualWield;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_libraries.DungeonsLibraries.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final Capability<RangedAbilities> RANGED_ABILITIES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<Combo> COMBO_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<DualWield> DUAL_WIELD_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public static void setupCapabilities() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addGenericListener(ItemStack.class, AttacherRangedAbilities::attach);
        forgeBus.addGenericListener(Entity.class, AttacherCombo::attach);
        forgeBus.addGenericListener(ItemStack.class, AttacherDualWield::attach);
    }

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(RangedAbilities.class);
        event.register(Combo.class);
        event.register(Combo.class);
    }
}
