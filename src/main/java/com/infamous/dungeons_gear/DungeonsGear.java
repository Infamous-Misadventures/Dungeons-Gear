package com.infamous.dungeons_gear;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboStorage;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.summoning.*;
import com.infamous.dungeons_gear.capabilities.weapon.IWeapon;
import com.infamous.dungeons_gear.capabilities.weapon.Weapon;
import com.infamous.dungeons_gear.capabilities.weapon.WeaponStorage;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.entities.IceCloudRenderer;
import com.infamous.dungeons_gear.groups.ArmorGroup;
import com.infamous.dungeons_gear.groups.ArtifactGroup;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.init.ParticleInit;
import com.infamous.dungeons_gear.items.VanillaItemModelProperties;
import com.infamous.dungeons_gear.items.BowItemModelsProperties;
import com.infamous.dungeons_gear.items.CrossbowItemModelsProperties;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_gear.groups.MeleeWeaponGroup;
import com.infamous.dungeons_gear.groups.RangedWeaponGroup;
import com.infamous.dungeons_gear.melee.WeaponAttributeHandler;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("dungeons_gear")
public class DungeonsGear
{
    // Directly reference a log4j logger.
    public static final String MODID = "dungeons_gear";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup MELEE_WEAPON_GROUP = new MeleeWeaponGroup();
    public static final ItemGroup RANGED_WEAPON_GROUP = new RangedWeaponGroup();
    public static final ItemGroup ARTIFACT_GROUP = new ArtifactGroup();
    public static final ItemGroup ARMOR_GROUP = new ArmorGroup();

    public static CommonProxy PROXY;

    public DungeonsGear() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DungeonsGearConfig.COMMON_SPEC);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);


        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ParticleInit.PARTICLES.register(modEventBus);
        AttributeRegistry.ATTRIBUTES.register(modEventBus);
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        DeferredWorkQueue.runLater(NetworkHandler::init);
        WeaponAttributeHandler.setWeaponAttributeModifiers();
        // some preinit code
        CapabilityManager.INSTANCE.register(ISummonable.class, new SummonableStorage(), Summonable::new);
        CapabilityManager.INSTANCE.register(ICombo.class, new ComboStorage(), Combo::new);
        CapabilityManager.INSTANCE.register(ISummoner.class, new SummonerStorage(), Summoner::new);
        CapabilityManager.INSTANCE.register(IWeapon.class, new WeaponStorage(), Weapon::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new CrossbowItemModelsProperties());
        MinecraftForge.EVENT_BUS.register(new BowItemModelsProperties());
        MinecraftForge.EVENT_BUS.register(new VanillaItemModelProperties());

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ICE_CLOUD.get(), IceCloudRenderer::new);
    }
}
