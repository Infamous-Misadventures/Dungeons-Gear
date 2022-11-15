package com.infamous.dungeons_gear;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import com.infamous.dungeons_gear.client.ClientProxy;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_gear.groups.ArmorGroup;
import com.infamous.dungeons_gear.groups.ArtifactGroup;
import com.infamous.dungeons_gear.groups.MeleeWeaponGroup;
import com.infamous.dungeons_gear.groups.RangedWeaponGroup;
import com.infamous.dungeons_gear.items.DualWieldItemProperties;
import com.infamous.dungeons_gear.items.GearRangedItemModelProperties;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.armor.PetBatArmorGear;
import com.infamous.dungeons_gear.loot.LootConditionRegistry;
import com.infamous.dungeons_gear.loot.ModLootFunctionTypes;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.registry.AttributeRegistry;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.registry.ParticleInit;
import com.infamous.dungeons_libraries.items.gearconfig.client.ArmorGearRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DungeonsGear.MODID)
public class DungeonsGear
{
    // Directly reference a log4j logger.
    public static final String MODID = "dungeons_gear";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab MELEE_WEAPON_GROUP = new MeleeWeaponGroup();
    public static final CreativeModeTab RANGED_WEAPON_GROUP = new RangedWeaponGroup();
    public static final CreativeModeTab ARTIFACT_GROUP = new ArtifactGroup();
    public static final CreativeModeTab ARMOR_GROUP = new ArmorGroup();

    public static CommonProxy PROXY;

    public DungeonsGear() {

        new DungeonsGearConfig();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);


        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ParticleInit.PARTICLES.register(modEventBus);
        AttributeRegistry.ATTRIBUTES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addGenericListener(Block.class, (Consumer<RegistryEvent.Register<Block>>) blockRegister ->
                        LootConditionRegistry.init()
                );

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCapabilities.setupCapabilities();
    }

    private void processIMC(final InterModProcessEvent event) {
        DungeonsGearCompatibility.checkCompatStatus();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        NetworkHandler.init();
        event.enqueueWork(ModLootFunctionTypes::register);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new DualWieldItemProperties());
        GearRangedItemModelProperties.init();

        GeoArmorRenderer.registerArmorRenderer(FreezingResistanceArmorGear.class, ArmorGearRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(PetBatArmorGear.class, ArmorGearRenderer::new);
    }
}
