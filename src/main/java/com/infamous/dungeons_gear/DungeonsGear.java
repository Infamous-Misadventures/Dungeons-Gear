package com.infamous.dungeons_gear;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import com.infamous.dungeons_gear.client.ClientProxy;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.entities.SoulWizardEntity;
import com.infamous.dungeons_gear.groups.ArmorGroup;
import com.infamous.dungeons_gear.groups.ArtifactGroup;
import com.infamous.dungeons_gear.groups.MeleeWeaponGroup;
import com.infamous.dungeons_gear.groups.RangedWeaponGroup;
import com.infamous.dungeons_gear.items.DualWieldItemProperties;
import com.infamous.dungeons_gear.items.GearRangedItemModelProperties;
import com.infamous.dungeons_gear.registry.*;
import com.infamous.dungeons_gear.registry.GlobalLootModifierInit;
import com.infamous.dungeons_gear.loot.ModLootFunctionTypes;
import com.infamous.dungeons_gear.network.NetworkHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.registry.SoundRegistry;

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
        // Register the initEntityTypeAttributes method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initEntityTypeAttributes);


        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        ParticleInit.PARTICLES.register(modEventBus);
        AttributeInit.ATTRIBUTES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        GlobalLootModifierInit.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        LootConditionInit.LOOT_ITEM_CONDITION_TYPES.register(modEventBus);
        MobEffectInit.MOB_EFFECTS.register(modEventBus);
        PotionInit.POTIONS.register(modEventBus);
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

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

    public void initEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.SOUL_WIZARD.get(), SoulWizardEntity.setCustomAttributes().build());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new DualWieldItemProperties());
        GearRangedItemModelProperties.init();
    }
}
