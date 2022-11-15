package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.items.ArrowBundleItem;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.armor.PetBatArmorGear;
import com.infamous.dungeons_gear.items.artifacts.*;
import com.infamous.dungeons_gear.items.artifacts.beacon.CorruptedBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.CorruptedPumpkinItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.EyeOfTheGuardianItem;
import com.infamous.dungeons_gear.items.melee.DualWieldMeleeGear;
import com.infamous.dungeons_gear.items.melee.ShearsGear;
import com.infamous.dungeons_gear.items.melee.StaffGear;
import com.infamous.dungeons_gear.items.ranged.crossbows.DualCrossbowGear;
import com.infamous.dungeons_gear.loot.LootTableType;
import com.infamous.dungeons_gear.utilties.GeneralHelper;
import com.infamous.dungeons_libraries.DungeonsLibraries;
import com.infamous.dungeons_libraries.items.gearconfig.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.RANGED_WEAPON_GROUP;
import static com.infamous.dungeons_gear.loot.LootTableType.*;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final Map<ResourceLocation, RegistryObject<Item>> ARMORS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> MELEE_WEAPONS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> RANGED_WEAPONS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> ARTIFACTS = new HashMap<>();
    public static final Map<LootTableType, List<RegistryObject<Item>>> LOOT_TABLES = new HashMap<>();


    public static final Item.Properties MELEE_WEAPON_PROPERTIES = DungeonsGearConfig.ENABLE_MELEE_WEAPON_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.MELEE_WEAPON_GROUP) : new Item.Properties().tab(CreativeModeTab.TAB_COMBAT);
    public static final Item.Properties ARMOR_PROPERTIES = DungeonsGearConfig.ENABLE_ARMOR_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.ARMOR_GROUP) : new Item.Properties().tab(CreativeModeTab.TAB_COMBAT);
    public static final Item.Properties RANGED_WEAPON_PROPERTIES = DungeonsGearConfig.ENABLE_RANGED_WEAPON_TAB.get() ?
            new Item.Properties().tab(RANGED_WEAPON_GROUP) : new Item.Properties().tab(CreativeModeTab.TAB_COMBAT);
    public static final Item.Properties ARTIFACT_PROPERTIES = DungeonsGearConfig.ENABLE_ARTIFACT_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.ARTIFACT_GROUP) : new Item.Properties().tab(CreativeModeTab.TAB_COMBAT);

    //DPS 9.6 (19.2), crits once per 2.5 (1.25) seconds
    public static final RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FANG_OF_FROST = registerMeleeWeapon("fang_of_frost",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> MOON_DAGGER = registerMeleeWeapon("moon_dagger",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> SHEAR_DAGGER = registerMeleeWeapon("shear_dagger",
            () -> new ShearsGear(MELEE_WEAPON_PROPERTIES), BASIC);
    //DPS 10 (20), crits once per 3 (1.5) seconds
    public static final RegistryObject<Item> SICKLE = registerMeleeWeapon("sickle",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> NIGHTMARES_BITE = registerMeleeWeapon("nightmares_bite",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> THE_LAST_LAUGH = registerMeleeWeapon("the_last_laugh",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    //DPS 9.6/8 (19.2/16), crits once per 2.92/1 (1.46/0.5) seconds
    public static final RegistryObject<Item> GAUNTLET = registerMeleeWeapon("gauntlet",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FIGHTERS_BINDING = registerMeleeWeapon("fighters_binding",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> MAULER = registerMeleeWeapon("mauler",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), JUNGLE);
    public static final RegistryObject<Item> SOUL_FIST = registerMeleeWeapon("soul_fist",
            () -> new DualWieldMeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 8, crits once per 3.5 seconds
    public static final RegistryObject<Item> RAPIER = registerMeleeWeapon("rapier",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> BEE_STINGER = registerMeleeWeapon("bee_stinger",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FREEZING_FOIL = registerMeleeWeapon("freezing_foil",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);

    //DPS 9.1, crits once per 1.53 seconds
    public static final RegistryObject<Item> SOUL_SCYTHE = registerMeleeWeapon("soul_scythe",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> FROST_SCYTHE = registerMeleeWeapon("frost_scythe",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> JAILORS_SCYTHE = registerMeleeWeapon("jailors_scythe",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);

    //DPS 7.2, crits once per 1.11 seconds
    public static final RegistryObject<Item> CUTLASS = registerMeleeWeapon("cutlass",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> DANCERS_SWORD = registerMeleeWeapon("dancers_sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> NAMELESS_BLADE = registerMeleeWeapon("nameless_blade",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> SPARKLER = registerMeleeWeapon("sparkler",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);

    //DPS 9.6, crits once per 1.875 seconds
    public static final RegistryObject<Item> SWORD = registerMeleeWeapon("sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> STONE_SWORD = registerMeleeWeapon("stone_sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);;
    public static final RegistryObject<Item> GOLD_SWORD = registerMeleeWeapon("gold_sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> DIAMOND_SWORD = registerMeleeWeapon("diamond_sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HAWKBRAND = registerMeleeWeapon("hawkbrand",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SINISTER_SWORD = registerMeleeWeapon("sinister_sword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 4.8, crits once per 0.83 seconds
    public static final RegistryObject<Item> PICKAXE = registerMeleeWeapon("pickaxe",
            () -> new PickaxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> DIAMOND_PICKAXE = registerMeleeWeapon("diamond_pickaxe",
            () -> new PickaxeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 12, crits once per 5 seconds
    public static final RegistryObject<Item> BATTLESTAFF = registerMeleeWeapon("battlestaff",
            () -> new StaffGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> BATTLESTAFF_OF_TERROR = registerMeleeWeapon("battlestaff_of_terror",
            () -> new StaffGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> GROWING_STAFF = registerMeleeWeapon("growing_staff",
            () -> new StaffGear(MELEE_WEAPON_PROPERTIES), DESERT);

    //DPS 10, crits once per 3 seconds, disables shields
    public static final RegistryObject<Item> AXE = registerMeleeWeapon("axe",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GOLD_AXE = registerMeleeWeapon("gold_axe",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FIREBRAND = registerMeleeWeapon("firebrand",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HIGHLAND_AXE = registerMeleeWeapon("highland_axe",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 9, crits once per 1.1 seconds, disables shields
    public static final RegistryObject<Item> DOUBLE_AXE = registerMeleeWeapon("double_axe",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> CURSED_AXE = registerMeleeWeapon("cursed_axe",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> WHIRLWIND = registerMeleeWeapon("whirlwind",
            () -> new AxeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 9.8, crits once per 2.1 seconds, disables shields
    public static final RegistryObject<Item> MACE = registerMeleeWeapon("mace",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FLAIL = registerMeleeWeapon("flail",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SUNS_GRACE = registerMeleeWeapon("suns_grace",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 8.8, crits once per 0.9 seconds, disables shields
    public static final RegistryObject<Item> GREAT_HAMMER = registerMeleeWeapon("great_hammer",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HAMMER_OF_GRAVITY = registerMeleeWeapon("hammer_of_gravity",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> STORMLANDER = registerMeleeWeapon("stormlander",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 11.2, crits once per 2.1 seconds
    public static final RegistryObject<Item> KATANA = registerMeleeWeapon("katana",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> DARK_KATANA = registerMeleeWeapon("dark_katana",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> MASTERS_KATANA = registerMeleeWeapon("masters_katana",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 7.2, crits once per 1.25 seconds
    public static final RegistryObject<Item> SOUL_KNIFE = registerMeleeWeapon("soul_knife",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> ETERNAL_KNIFE = registerMeleeWeapon("eternal_knife",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> TRUTHSEEKER = registerMeleeWeapon("truthseeker",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);

    //DPS 9.6, crits once per 3.3 seconds
    public static final RegistryObject<Item> CLAYMORE = registerMeleeWeapon("claymore",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> BROADSWORD = registerMeleeWeapon("broadsword",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HEARTSTEALER = registerMeleeWeapon("heartstealer",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GREAT_AXEBLADE = registerMeleeWeapon("great_axeblade",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FROST_SLAYER = registerMeleeWeapon("frost_slayer",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);

    //DPS 8.4, crits once per 2.14 seconds, +2 reach
    public static final RegistryObject<Item> SPEAR = registerMeleeWeapon("spear",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FORTUNE_SPEAR = registerMeleeWeapon("fortune_spear",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> WHISPERING_SPEAR = registerMeleeWeapon("whispering_spear",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), BASIC);

    //DPS 9.6, crits once per 2.5 seconds, +2 reach
    public static final RegistryObject<Item> GLAIVE = registerMeleeWeapon("glaive",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> GRAVE_BANE = registerMeleeWeapon("grave_bane",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> VENOM_GLAIVE = registerMeleeWeapon("venom_glaive",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), DESERT);

    //DPS 7, crits once per second, +2 reach
    public static final RegistryObject<Item> WHIP = registerMeleeWeapon("whip",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), JUNGLE);
    public static final RegistryObject<Item> VINE_WHIP = registerMeleeWeapon("vine_whip",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), JUNGLE);

    //DPS 7.2, crits once per 1.25 seconds
    public static final RegistryObject<Item> TEMPEST_KNIFE = registerMeleeWeapon("tempest_knife",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> RESOLUTE_TEMPEST_KNIFE = registerMeleeWeapon("resolute_tempest_knife",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> CHILL_GALE_KNIFE = registerMeleeWeapon("chill_gale_knife",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), COLD);

    //DPS 10.4, crits once per 2.5 seconds
    public static final RegistryObject<Item> BONECLUB = registerMeleeWeapon("boneclub",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), NETHER);
    public static final RegistryObject<Item> BONE_CUDGEL = registerMeleeWeapon("bone_cudgel",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), NETHER);

    // sawblades TODO: How the bloody murder am I supposed to change this thing?
//    public static final RegistryObject<Item> BROKEN_SAWBLADE = registerMeleeWeapon("broken_sawblade",
//            () -> new SawbladeItem(5, (3.0f-4.0f), MELEE_WEAPON_PROPERTIES, false));
//    public static final RegistryObject<Item> MECHANIZED_SAWBLADE = registerMeleeWeapon("mechanized_sawblade",
//            () -> new SawbladeItem(5, (3.0f-4.0f), MELEE_WEAPON_PROPERTIES, true));

    //DPS 7.2, crits once per 1.25 seconds
//    public static final RegistryObject<Item> CORAL_BLADE = registerMeleeWeapon("coral_blade",
//            () -> new CoralBladeItem(2, (2.4f-4.0f), MELEE_WEAPON_PROPERTIES, false));
//    public static final RegistryObject<Item> SPONGE_STRIKER = registerMeleeWeapon("sponge_striker",
//            () -> new CoralBladeItem(2, (2.4f-4.0f), MELEE_WEAPON_PROPERTIES, true));

    //DPS 8, crits once per 1.25 seconds
    public static final RegistryObject<Item> ANCHOR = registerMeleeWeapon("anchor",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> ENCRUSTED_ANCHOR = registerMeleeWeapon("encrusted_anchor",
            () -> new MeleeGear(MELEE_WEAPON_PROPERTIES), OCEAN);


    public static final RegistryObject<Item> BONEBOW = registerRangedWeapon("bonebow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> TWIN_BOW = registerRangedWeapon("twin_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HAUNTED_BOW = registerRangedWeapon("haunted_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> SOUL_BOW = registerRangedWeapon("soul_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> BOW_OF_LOST_SOULS = registerRangedWeapon("bow_of_lost_souls",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), NETHER);
    public static final RegistryObject<Item> NOCTURNAL_BOW = registerRangedWeapon("nocturnal_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SHIVERING_BOW = registerRangedWeapon("shivering_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> POWER_BOW = registerRangedWeapon("power_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> ELITE_POWER_BOW = registerRangedWeapon("elite_power_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SABREWING = registerRangedWeapon("sabrewing",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> LONGBOW = registerRangedWeapon("longbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GUARDIAN_BOW = registerRangedWeapon("guardian_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> RED_SNAKE = registerRangedWeapon("red_snake",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> HUNTING_BOW = registerRangedWeapon("hunting_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HUNTERS_PROMISE = registerRangedWeapon("hunters_promise",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> MASTERS_BOW = registerRangedWeapon("masters_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> ANCIENT_BOW = registerRangedWeapon("ancient_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> SHORTBOW = registerRangedWeapon("shortbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> MECHANICAL_SHORTBOW = registerRangedWeapon("mechanical_shortbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> PURPLE_STORM = registerRangedWeapon("purple_storm",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> LOVE_SPELL_BOW = registerRangedWeapon("love_spell_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> TRICKBOW = registerRangedWeapon("trickbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> THE_GREEN_MENACE = registerRangedWeapon("the_green_menace",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> THE_PINK_SCOUNDREL = registerRangedWeapon("the_pink_scoundrel",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SUGAR_RUSH = registerRangedWeapon("sugar_rush",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> SNOW_BOW = registerRangedWeapon("snow_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> WINTERS_TOUCH = registerRangedWeapon("winters_touch",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), COLD);

    public static final RegistryObject<Item> WIND_BOW = registerRangedWeapon("wind_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> BURST_GALE_BOW = registerRangedWeapon("burst_gale_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), COLD);
    public static final RegistryObject<Item> ECHO_OF_THE_VALLEY = registerRangedWeapon("echo_of_the_valley",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES), COLD);


    public static final RegistryObject<Item> RAPID_CROSSBOW = registerRangedWeapon("rapid_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> BUTTERFLY_CROSSBOW = registerRangedWeapon("butterfly_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> AUTO_CROSSBOW = registerRangedWeapon("auto_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> AZURE_SEEKER = registerRangedWeapon("azure_seeker",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> THE_SLICER = registerRangedWeapon("the_slicer",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> HEAVY_CROSSBOW = registerRangedWeapon("heavy_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> DOOM_CROSSBOW = registerRangedWeapon("doom_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SLAYER_CROSSBOW = registerRangedWeapon("slayer_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> SOUL_CROSSBOW = registerRangedWeapon("soul_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), NETHER);
    public static final RegistryObject<Item> FERAL_SOUL_CROSSBOW = registerRangedWeapon("feral_soul_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), JUNGLE);
    public static final RegistryObject<Item> VOIDCALLER = registerRangedWeapon("voidcaller",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), END);

    public static final RegistryObject<Item> SCATTER_CROSSBOW = registerRangedWeapon("scatter_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HARP_CROSSBOW = registerRangedWeapon("harp_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> LIGHTNING_HARP_CROSSBOW = registerRangedWeapon("lightning_harp_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> EXPLODING_CROSSBOW = registerRangedWeapon("exploding_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FIREBOLT_THROWER = registerRangedWeapon("firebolt_thrower",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> IMPLODING_CROSSBOW = registerRangedWeapon("imploding_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> BURST_CROSSBOW = registerRangedWeapon("burst_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> CORRUPTED_CROSSBOW = registerRangedWeapon("corrupted_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SOUL_HUNTER_CROSSBOW = registerRangedWeapon("soul_hunter_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), BASIC);

    public static final RegistryObject<Item> DUAL_CROSSBOW = registerRangedWeapon("dual_crossbow",
            () -> new DualCrossbowGear(RANGED_WEAPON_PROPERTIES), DESERT);
    public static final RegistryObject<Item> BABY_CROSSBOW = registerRangedWeapon("baby_crossbow",
            () -> new DualCrossbowGear(RANGED_WEAPON_PROPERTIES), DESERT);

    public static final RegistryObject<Item> HARPOON_CROSSBOW = registerRangedWeapon("harpoon_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> NAUTICAL_CROSSBOW = registerRangedWeapon("nautical_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES), OCEAN);

    public static final RegistryObject<Item> BOOTS_OF_SWIFTNESS = registerArtifact("boots_of_swiftness",
            () -> new BootsOfSwiftnessItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> DEATH_CAP_MUSHROOM = registerArtifact("death_cap_mushroom",
            () -> new DeathCapMushroomItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GOLEM_KIT = registerArtifact("golem_kit",
            () -> new GolemKitItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> TASTY_BONE = registerArtifact("tasty_bone",
            () -> new TastyBoneItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> WONDERFUL_WHEAT = registerArtifact("wonderful_wheat",
            () -> new WonderfulWheatItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GONG_OF_WEAKENING = registerArtifact("gong_of_weakening",
            () -> new GongOfWeakeningItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> LIGHTNING_ROD = registerArtifact("lightning_rod",
            () -> new LightningRodItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> IRON_HIDE_AMULET = registerArtifact("iron_hide_amulet",
            () -> new IronHideAmuletItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> LOVE_MEDALLION = registerArtifact("love_medallion",
            () -> new LoveMedallionItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> GHOST_CLOAK = registerArtifact("ghost_cloak",
            () -> new GhostCloakItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HARVESTER = registerArtifact("harvester",
            () -> new HarvesterItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> SHOCK_POWDER = registerArtifact("shock_powder",
            () -> new ShockPowderItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> CORRUPTED_SEEDS = registerArtifact("corrupted_seeds",
            () -> new CorruptedSeedsItem(ARTIFACT_PROPERTIES), JUNGLE);
    public static final RegistryObject<Item> ICE_WAND = registerArtifact("ice_wand",
            () -> new IceWandItem(ARTIFACT_PROPERTIES), COLD);
    public static final RegistryObject<Item> WIND_HORN = registerArtifact("wind_horn",
            () -> new WindHornItem(ARTIFACT_PROPERTIES), COLD);
    public static final RegistryObject<Item> SOUL_HEALER = registerArtifact("soul_healer",
            () -> new SoulHealerItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> LIGHT_FEATHER = registerArtifact("light_feather",
            () -> new LightFeatherItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> FLAMING_QUIVER = registerArtifact("flaming_quiver",
            () -> new FlamingQuiverItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> TORMENT_QUIVER = registerArtifact("torment_quiver",
            () -> new TormentQuiverItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> TOTEM_OF_REGENERATION = registerArtifact("totem_of_regeneration",
            () -> new TotemOfRegenerationItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> TOTEM_OF_SHIELDING = registerArtifact("totem_of_shielding",
            () -> new TotemOfShieldingItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> TOTEM_OF_SOUL_PROTECTION = registerArtifact("totem_of_soul_protection",
            () -> new TotemOfSoulProtectionItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> CORRUPTED_BEACON = registerArtifact("corrupted_beacon",
            () -> new CorruptedBeaconItem(ARTIFACT_PROPERTIES), DESERT);
    public static final RegistryObject<Item> BUZZY_NEST = registerArtifact("buzzy_nest",
            () -> new BuzzyNestItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> ENCHANTED_GRASS = registerArtifact("enchanted_grass",
            () -> new EnchantedGrassItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> CORRUPTED_PUMPKIN = registerArtifact("corrupted_pumpkin",
            () -> new CorruptedPumpkinItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> THUNDERING_QUIVER = registerArtifact("thundering_quiver",
            () -> new ThunderingQuiverItem(ARTIFACT_PROPERTIES), BASIC);
    public static final RegistryObject<Item> HARPOON_QUIVER = registerArtifact("harpoon_quiver",
            () -> new HarpoonQuiverItem(ARTIFACT_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> SATCHEL_OF_ELIXIRS = registerArtifact("satchel_of_elixirs",
            () -> new SatchelOfElixirsItem(ARTIFACT_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> SATCHEL_OF_SNACKS = registerArtifact("satchel_of_snacks",
            () -> new SatchelOfSnacksItem(ARTIFACT_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> SATCHEL_OF_ELEMENTS = registerArtifact("satchel_of_elements",
            () -> new SatchelOfElementsItem(ARTIFACT_PROPERTIES), COLD);
    public static final RegistryObject<Item> POWERSHAKER = registerArtifact("powershaker",
            () -> new PowershakerItem(ARTIFACT_PROPERTIES), NETHER);
    public static final RegistryObject<Item> UPDRAFT_TOME = registerArtifact("updraft_tome",
            () -> new UpdraftTomeItem(ARTIFACT_PROPERTIES), COLD);
    public static final RegistryObject<Item> EYE_OF_THE_GUARDIAN = registerArtifact("eye_of_the_guardian",
            () -> new EyeOfTheGuardianItem(ARTIFACT_PROPERTIES), OCEAN);
    public static final RegistryObject<Item> FIREWORKS_DISPLAY = registerArtifact("fireworks_display",
            () -> new FireworksDisplayItem(ARTIFACT_PROPERTIES), GIFT);
    public static final RegistryObject<Item> SOUL_LANTERN = registerArtifact("soul_lantern",
            () -> new SoulLanternItem(ARTIFACT_PROPERTIES), BASIC);

    public static final ArmorSet HUNTERS_ARMOR = registerArmorSet("hunters", BASIC);
    public static final ArmorSet ARCHERS_ARMOR = registerArmorSet("archers", BASIC);
    public static final ArmorSet BATTLE_ROBES = registerArmorSet("battle_robes", BASIC);
    public static final ArmorSet CHAMPIONS_ARMOR = registerArmorSet("champions", BASIC);
    public static final ArmorSet HEROS_ARMOR = registerArmorSet("heros", BASIC);
    public static final ArmorSet DARK_ARMOR = registerArmorSet("dark", BASIC);
    public static final ArmorSet TITANS_SHROUD = registerArmorSet("titans_shroud", BASIC);
    public static final ArmorSet EVOCATION_ROBES = registerArmorSet("evocation_robes", BASIC);
    public static final ArmorSet EMBER_ROBES = registerArmorSet("ember_robes", BASIC);
    public static final ArmorSet GRIM_ARMOR = registerArmorSet("grim", BASIC);
    public static final ArmorSet WITHER_ARMOR = registerArmorSet("wither", BASIC);
    public static final ArmorSet GUARDS_ARMOR = registerArmorSet("guards", BASIC);
    public static final ArmorSet CURIOUS_ARMOR = registerArmorSet("curious", BASIC);
    public static final ArmorSet MERCENARY_ARMOR = registerArmorSet("mercenary", BASIC);
    public static final ArmorSet RENEGADE_ARMOR = registerArmorSet("renegade", BASIC);
    public static final ArmorSet HUNGRY_HORROR = registerArmorSet("hungry_horror", BASIC);
    public static final ArmorSet OCELOT_ARMOR = registerArmorSet("ocelot", BASIC);
    public static final ArmorSet SHADOW_WALKER_ARMOR = registerArmorSet("shadow_walker", BASIC);
    public static final ArmorSet PHANTOM_ARMOR = registerArmorSet("phantom", BASIC);
    public static final ArmorSet FROST_BITE = registerArmorSet("frost_bite", BASIC);
    public static final ArmorSet PLATE_ARMOR = registerArmorSet("plate", BASIC);
    public static final ArmorSet FULL_METAL_ARMOR = registerArmorSet("full_metal", BASIC);
    public static final ArmorSet REINFORCED_MAIL = registerArmorSet("reinforced_mail", BASIC);
    public static final ArmorSet STALWART_ARMOR = registerArmorSet("stalwart", BASIC);
    public static final ArmorSet SCALE_MAIL = registerArmorSet("scale_mail", BASIC);
    public static final ArmorSet HIGHLAND_ARMOR = registerArmorSet("highland", BASIC);
    public static final ArmorSet SNOW_ARMOR = registerFreezingResistanceArmorSet("snow", BASIC);
    public static final ArmorSet FROST_ARMOR = registerFreezingResistanceArmorSet("frost", BASIC);
    public static final ArmorSet SOUL_ROBES = registerArmorSet("soul", BASIC);
    public static final ArmorSet SOULDANCER_ROBES = registerArmorSet("souldancer", BASIC);
    public static final ArmorSet SPELUNKER_ARMOR = registePetBatArmorSet("spelunker", BASIC);
    public static final ArmorSet CAVE_CRAWLER = registePetBatArmorSet("cave_crawler", BASIC);
    public static final ArmorSet THIEF_ARMOR = registerArmorSet("thief", BASIC);
    public static final ArmorSet SPIDER_ARMOR = registerArmorSet("spider", BASIC);
    public static final ArmorSet WOLF_ARMOR = registerArmorSet("wolf", BASIC);
    public static final ArmorSet FOX_ARMOR = registerArmorSet("fox", BASIC);
    public static final ArmorSet ARCTIC_FOX_ARMOR = registerArmorSet("arctic_fox", COLD);
    public static final ArmorSet CLIMBING_GEAR = registerArmorSet("climbing", COLD);
    public static final ArmorSet RUGGED_CLIMBING_GEAR = registerFreezingResistanceArmorSet("rugged_climbing", COLD);
    public static final ArmorSet GOAT_GEAR = registerArmorSet("goat", COLD);
    public static final ArmorSet EMERALD_ARMOR = registerArmorSet("emerald", COLD);
    public static final ArmorSet GILDED_GLORY = registerArmorSet("gilded_glory", COLD);
    public static final ArmorSet OPULENT_ARMOR = registerArmorSet("opulent", COLD);
    public static final ArmorSet BEENEST = registerArmorSet("beenest", JUNGLE);
    public static final ArmorSet BEEHIVE = registerArmorSet("beehive", JUNGLE);

    public static final RegistryObject<Item> ARROW_BUNDLE = ITEMS.register("arrow_bundle",
            () -> new ArrowBundleItem(new Item.Properties().tab(RANGED_WEAPON_GROUP)));


    private static ArmorSet registerArmorSet(String armorSetName, LootTableType lootTableType, boolean animated) {
        String armorSetId = armorSetName+"_armor";
        ResourceLocation armorSetResource = new ResourceLocation(MODID, armorSetId);
        ResourceLocation modelLocation = new ResourceLocation(MODID, "geo/armor/"+armorSetId+".geo.json");
        ResourceLocation textureLocation = new ResourceLocation(MODID, "textures/models/armor/"+armorSetId+".png");
        ResourceLocation animationFileLocation = animated ? new ResourceLocation(MODID, "animations/armor/" + armorSetId + ".animation.json") : new ResourceLocation(DungeonsLibraries.MODID, "animations/armor/armor_default.animation.json");
        ArmorSet armorSet = new ArmorSet(
                armorSetId,
                registerArmor(armorSetName+"_helmet", () -> new ArmorGear(EquipmentSlot.HEAD, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_chestplate", () -> new ArmorGear(EquipmentSlot.CHEST, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_leggings", () -> new ArmorGear(EquipmentSlot.LEGS, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_boots", () -> new ArmorGear(EquipmentSlot.FEET, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType)
        );
        return armorSet;
    }

    private static ArmorSet registerArmorSet(String armorSetId, LootTableType lootTableType) {
        return registerArmorSet(armorSetId, lootTableType, false);
    }

    private static ArmorSet registerFreezingResistanceArmorSet(String armorSetName, LootTableType lootTableType) {
        String armorSetId = armorSetName+"_armor";
        ResourceLocation armorSetResource = new ResourceLocation(MODID, armorSetId+"_armor");
        ResourceLocation modelLocation = new ResourceLocation(MODID, "geo/armor/"+armorSetId+".geo.json");
        ResourceLocation textureLocation = new ResourceLocation(MODID, "textures/models/armor/"+armorSetId+".png");
        ResourceLocation animationFileLocation = new ResourceLocation(DungeonsLibraries.MODID, "animations/armor/armor_default.animation.json");
        ArmorSet armorSet = new ArmorSet(
                armorSetId,
                registerArmor(armorSetName+"_helmet", () -> new FreezingResistanceArmorGear(EquipmentSlot.HEAD, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_chestplate", () -> new FreezingResistanceArmorGear(EquipmentSlot.CHEST, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_leggings", () -> new FreezingResistanceArmorGear(EquipmentSlot.LEGS, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_boots", () -> new FreezingResistanceArmorGear(EquipmentSlot.FEET, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType)
        );
        return armorSet;
    }

    private static ArmorSet registePetBatArmorSet(String armorSetName, LootTableType lootTableType) {
        String armorSetId = armorSetName+"_armor";
        ResourceLocation armorSetResource = new ResourceLocation(MODID, armorSetId);
        ResourceLocation modelLocation = new ResourceLocation(MODID, "geo/armor/"+armorSetId+".geo.json");
        ResourceLocation textureLocation = new ResourceLocation(MODID, "textures/models/armor/"+armorSetId+".png");
        ResourceLocation animationFileLocation = new ResourceLocation(MODID, "animations/armor/" + armorSetId + ".animation.json");
        ArmorSet armorSet = new ArmorSet(
                armorSetId,
                registerArmor(armorSetName+"_helmet", () -> new PetBatArmorGear(EquipmentSlot.HEAD, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_chestplate", () -> new PetBatArmorGear(EquipmentSlot.CHEST, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_leggings", () -> new PetBatArmorGear(EquipmentSlot.LEGS, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType),
                registerArmor(armorSetName+"_boots", () -> new PetBatArmorGear(EquipmentSlot.FEET, ARMOR_PROPERTIES, armorSetResource, modelLocation, textureLocation, animationFileLocation), lootTableType)
        );
        return armorSet;
    }

    private static RegistryObject<Item> registerArmor(String armorId, Supplier<Item> itemSupplier, LootTableType lootTableType) {
        if(armorId == null) return null;
        RegistryObject<Item> register = ITEMS.register(armorId, itemSupplier);
        ARMORS.put(GeneralHelper.modLoc(armorId), register);
        addToLootTables(lootTableType, register);
        return register;
    }

    private static RegistryObject<Item> registerMeleeWeapon(String meleeWeaponId, Supplier<Item> itemSupplier, LootTableType lootTableType) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        MELEE_WEAPONS.put(GeneralHelper.modLoc(meleeWeaponId), register);
        addToLootTables(lootTableType, register);
        return register;
    }

    private static RegistryObject<Item> registerRangedWeapon(String meleeWeaponId, Supplier<Item> itemSupplier, LootTableType lootTableType) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        RANGED_WEAPONS.put(GeneralHelper.modLoc(meleeWeaponId), register);
        addToLootTables(lootTableType, register);
        return register;
    }

    private static RegistryObject<Item> registerArtifact(String meleeWeaponId, Supplier<Item> itemSupplier, LootTableType lootTableType) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        ARTIFACTS.put(GeneralHelper.modLoc(meleeWeaponId), register);
        addToLootTables(lootTableType, register);
        return register;
    }

    private static void addToLootTables(LootTableType lootTableType, RegistryObject<Item> register) {
        List<RegistryObject<Item>> registryObjects = LOOT_TABLES.computeIfAbsent(lootTableType, lootTableTypeKey -> new ArrayList<>());
        registryObjects.add(register);
    }

}
