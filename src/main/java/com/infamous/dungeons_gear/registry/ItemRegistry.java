package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.items.ArmorMaterialList;
import com.infamous.dungeons_gear.items.ArrowBundleItem;
import com.infamous.dungeons_gear.items.ToolMaterialList;
import com.infamous.dungeons_gear.items.armor.*;
import com.infamous.dungeons_gear.items.artifacts.*;
import com.infamous.dungeons_gear.items.artifacts.beacon.CorruptedBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.CorruptedPumpkinItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.EyeOfTheGuardianItem;
import com.infamous.dungeons_gear.items.melee.DualWieldMeleeGear;
import com.infamous.dungeons_gear.items.melee.StaffGear;
import com.infamous.dungeons_gear.items.ranged.crossbows.DualCrossbowGear;
import com.infamous.dungeons_libraries.items.gearconfig.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.RANGED_WEAPON_GROUP;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final Map<ResourceLocation, RegistryObject<Item>> ARMORS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> MELEE_WEAPONS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> RANGED_WEAPONS = new HashMap<>();
    public static final Map<ResourceLocation, RegistryObject<Item>> ARTIFACTS = new HashMap<>();


    public static final Item.Properties MELEE_WEAPON_PROPERTIES = DungeonsGearConfig.ENABLE_MELEE_WEAPON_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.MELEE_WEAPON_GROUP) : new Item.Properties().tab(ItemGroup.TAB_COMBAT);
    public static final Item.Properties ARMOR_PROPERTIES = DungeonsGearConfig.ENABLE_ARMOR_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.ARMOR_GROUP) : new Item.Properties().tab(ItemGroup.TAB_COMBAT);
    public static final Item.Properties RANGED_WEAPON_PROPERTIES = DungeonsGearConfig.ENABLE_RANGED_WEAPON_TAB.get() ?
            new Item.Properties().tab(RANGED_WEAPON_GROUP) : new Item.Properties().tab(ItemGroup.TAB_COMBAT);
    public static final Item.Properties ARTIFACT_PROPERTIES = DungeonsGearConfig.ENABLE_ARTIFACT_TAB.get() ?
            new Item.Properties().tab(DungeonsGear.ARTIFACT_GROUP) : new Item.Properties().tab(ItemGroup.TAB_COMBAT);

    //DPS 9.6 (19.2), crits once per 2.5 (1.25) seconds
    public static final RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FANG_OF_FROST = registerMeleeWeapon("fang_of_frost",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> MOON_DAGGER = registerMeleeWeapon("moon_dagger",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SHEAR_DAGGER = registerMeleeWeapon("shear_dagger",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    //DPS 10 (20), crits once per 3 (1.5) seconds
    public static final RegistryObject<Item> SICKLE = registerMeleeWeapon("sickle",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> NIGHTMARES_BITE = registerMeleeWeapon("nightmares_bite",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> THE_LAST_LAUGH = registerMeleeWeapon("the_last_laugh",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    //DPS 9.6/8 (19.2/16), crits once per 2.92/1 (1.46/0.5) seconds
    public static final RegistryObject<Item> GAUNTLET = registerMeleeWeapon("gauntlet",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FIGHTERS_BINDING = registerMeleeWeapon("fighters_binding",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> MAULER = registerMeleeWeapon("mauler",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SOUL_FIST = registerMeleeWeapon("soul_fist",
            () -> new DualWieldMeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 8, crits once per 3.5 seconds
    public static final RegistryObject<Item> RAPIER = registerMeleeWeapon("rapier",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BEE_STINGER = registerMeleeWeapon("bee_stinger",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FREEZING_FOIL = registerMeleeWeapon("freezing_foil",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9.1, crits once per 1.53 seconds
    public static final RegistryObject<Item> SOUL_SCYTHE = registerMeleeWeapon("soul_scythe",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FROST_SCYTHE = registerMeleeWeapon("frost_scythe",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> JAILORS_SCYTHE = registerMeleeWeapon("jailors_scythe",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 7.2, crits once per 1.11 seconds
    public static final RegistryObject<Item> CUTLASS = registerMeleeWeapon("cutlass",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> DANCERS_SWORD = registerMeleeWeapon("dancers_sword",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> NAMELESS_BLADE = registerMeleeWeapon("nameless_blade",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SPARKLER = registerMeleeWeapon("sparkler",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9.6, crits once per 1.875 seconds
    public static final RegistryObject<Item> SWORD = registerMeleeWeapon("sword",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> STONE_SWORD = registerMeleeWeapon("stone_sword",
            () -> new MeleeGear(ToolMaterialList.STONE, MELEE_WEAPON_PROPERTIES));;
    public static final RegistryObject<Item> GOLD_SWORD = registerMeleeWeapon("gold_sword",
            () -> new MeleeGear(ToolMaterialList.GOLD, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> DIAMOND_SWORD = registerMeleeWeapon("diamond_sword",
            () -> new MeleeGear(ToolMaterialList.DIAMOND, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HAWKBRAND = registerMeleeWeapon("hawkbrand",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SINISTER_SWORD = registerMeleeWeapon("sinister_sword",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 4.8, crits once per 0.83 seconds
    public static final RegistryObject<Item> PICKAXE = registerMeleeWeapon("pickaxe",
            () -> new PickaxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> DIAMOND_PICKAXE = registerMeleeWeapon("diamond_pickaxe",
            () -> new PickaxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 12, crits once per 5 seconds
    public static final RegistryObject<Item> BATTLESTAFF = registerMeleeWeapon("battlestaff",
            () -> new StaffGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BATTLESTAFF_OF_TERROR = registerMeleeWeapon("battlestaff_of_terror",
            () -> new StaffGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> GROWING_STAFF = registerMeleeWeapon("growing_staff",
            () -> new StaffGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 10, crits once per 3 seconds, disables shields
    public static final RegistryObject<Item> AXE = registerMeleeWeapon("axe",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> GOLD_AXE = registerMeleeWeapon("gold_axe",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FIREBRAND = registerMeleeWeapon("firebrand",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HIGHLAND_AXE = registerMeleeWeapon("highland_axe",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9, crits once per 1.1 seconds, disables shields
    public static final RegistryObject<Item> DOUBLE_AXE = registerMeleeWeapon("double_axe",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> CURSED_AXE = registerMeleeWeapon("cursed_axe",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> WHIRLWIND = registerMeleeWeapon("whirlwind",
            () -> new AxeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9.8, crits once per 2.1 seconds, disables shields
    public static final RegistryObject<Item> MACE = registerMeleeWeapon("mace",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FLAIL = registerMeleeWeapon("flail",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SUNS_GRACE = registerMeleeWeapon("suns_grace",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 8.8, crits once per 0.9 seconds, disables shields
    public static final RegistryObject<Item> GREAT_HAMMER = registerMeleeWeapon("great_hammer",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HAMMER_OF_GRAVITY = registerMeleeWeapon("hammer_of_gravity",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> STORMLANDER = registerMeleeWeapon("stormlander",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 11.2, crits once per 2.1 seconds
    public static final RegistryObject<Item> KATANA = registerMeleeWeapon("katana",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> DARK_KATANA = registerMeleeWeapon("dark_katana",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> MASTERS_KATANA = registerMeleeWeapon("masters_katana",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 7.2, crits once per 1.25 seconds
    public static final RegistryObject<Item> SOUL_KNIFE = registerMeleeWeapon("soul_knife",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> ETERNAL_KNIFE = registerMeleeWeapon("eternal_knife",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> TRUTHSEEKER = registerMeleeWeapon("truthseeker",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9.6, crits once per 3.3 seconds
    public static final RegistryObject<Item> CLAYMORE = registerMeleeWeapon("claymore",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BROADSWORD = registerMeleeWeapon("broadsword",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HEARTSTEALER = registerMeleeWeapon("heartstealer",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> GREAT_AXEBLADE = registerMeleeWeapon("great_axeblade",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FROST_SLAYER = registerMeleeWeapon("frost_slayer",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 8.4, crits once per 2.14 seconds, +2 reach
    public static final RegistryObject<Item> SPEAR = registerMeleeWeapon("spear",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FORTUNE_SPEAR = registerMeleeWeapon("fortune_spear",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> WHISPERING_SPEAR = registerMeleeWeapon("whispering_spear",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 9.6, crits once per 2.5 seconds, +2 reach
    public static final RegistryObject<Item> GLAIVE = registerMeleeWeapon("glaive",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> GRAVE_BANE = registerMeleeWeapon("grave_bane",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> VENOM_GLAIVE = registerMeleeWeapon("venom_glaive",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 7, crits once per second, +2 reach
    public static final RegistryObject<Item> WHIP = registerMeleeWeapon("whip",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> VINE_WHIP = registerMeleeWeapon("vine_whip",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 7.2, crits once per 1.25 seconds
    public static final RegistryObject<Item> TEMPEST_KNIFE = registerMeleeWeapon("tempest_knife",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> RESOLUTE_TEMPEST_KNIFE = registerMeleeWeapon("resolute_tempest_knife",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> CHILL_GALE_KNIFE = registerMeleeWeapon("chill_gale_knife",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    //DPS 10.4, crits once per 2.5 seconds
    public static final RegistryObject<Item> BONECLUB = registerMeleeWeapon("boneclub",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BONE_CUDGEL = registerMeleeWeapon("bone_cudgel",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));

    // sawblades TODO: How the bloody murder am I supposed to change this thing?
//    public static final RegistryObject<Item> BROKEN_SAWBLADE = registerMeleeWeapon("broken_sawblade",
//            () -> new SawbladeItem(ToolMaterialList.METAL, 5, (3.0f-4.0f), MELEE_WEAPON_PROPERTIES, false));
//    public static final RegistryObject<Item> MECHANIZED_SAWBLADE = registerMeleeWeapon("mechanized_sawblade",
//            () -> new SawbladeItem(ToolMaterialList.METAL, 5, (3.0f-4.0f), MELEE_WEAPON_PROPERTIES, true));

    //DPS 7.2, crits once per 1.25 seconds
//    public static final RegistryObject<Item> CORAL_BLADE = registerMeleeWeapon("coral_blade",
//            () -> new CoralBladeItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), MELEE_WEAPON_PROPERTIES, false));
//    public static final RegistryObject<Item> SPONGE_STRIKER = registerMeleeWeapon("sponge_striker",
//            () -> new CoralBladeItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), MELEE_WEAPON_PROPERTIES, true));

    //DPS 8, crits once per 1.25 seconds
    public static final RegistryObject<Item> ANCHOR = registerMeleeWeapon("anchor",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> ENCRUSTED_ANCHOR = registerMeleeWeapon("encrusted_anchor",
            () -> new MeleeGear(ToolMaterialList.METAL, MELEE_WEAPON_PROPERTIES));


    public static final RegistryObject<Item> BONEBOW = registerRangedWeapon("bonebow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> TWIN_BOW = registerRangedWeapon("twin_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HAUNTED_BOW = registerRangedWeapon("haunted_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> SOUL_BOW = registerRangedWeapon("soul_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BOW_OF_LOST_SOULS = registerRangedWeapon("bow_of_lost_souls",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> NOCTURNAL_BOW = registerRangedWeapon("nocturnal_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SHIVERING_BOW = registerRangedWeapon("shivering_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> POWER_BOW = registerRangedWeapon("power_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> ELITE_POWER_BOW = registerRangedWeapon("elite_power_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SABREWING = registerRangedWeapon("sabrewing",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> LONGBOW = registerRangedWeapon("longbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> GUARDIAN_BOW = registerRangedWeapon("guardian_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> RED_SNAKE = registerRangedWeapon("red_snake",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> HUNTING_BOW = registerRangedWeapon("hunting_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HUNTERS_PROMISE = registerRangedWeapon("hunters_promise",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> MASTERS_BOW = registerRangedWeapon("masters_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> ANCIENT_BOW = registerRangedWeapon("ancient_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> SHORTBOW = registerRangedWeapon("shortbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> MECHANICAL_SHORTBOW = registerRangedWeapon("mechanical_shortbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> PURPLE_STORM = registerRangedWeapon("purple_storm",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> LOVE_SPELL_BOW = registerRangedWeapon("love_spell_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> TRICKBOW = registerRangedWeapon("trickbow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> THE_GREEN_MENACE = registerRangedWeapon("the_green_menace",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> THE_PINK_SCOUNDREL = registerRangedWeapon("the_pink_scoundrel",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SUGAR_RUSH = registerRangedWeapon("sugar_rush",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> SNOW_BOW = registerRangedWeapon("snow_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> WINTERS_TOUCH = registerRangedWeapon("winters_touch",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> WIND_BOW = registerRangedWeapon("wind_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BURST_GALE_BOW = registerRangedWeapon("burst_gale_bow",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> ECHO_OF_THE_VALLEY = registerRangedWeapon("echo_of_the_valley",
            () -> new BowGear(RANGED_WEAPON_PROPERTIES));


    public static final RegistryObject<Item> RAPID_CROSSBOW = registerRangedWeapon("rapid_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BUTTERFLY_CROSSBOW = registerRangedWeapon("butterfly_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> AUTO_CROSSBOW = registerRangedWeapon("auto_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> AZURE_SEEKER = registerRangedWeapon("azure_seeker",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> THE_SLICER = registerRangedWeapon("the_slicer",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> HEAVY_CROSSBOW = registerRangedWeapon("heavy_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> DOOM_CROSSBOW = registerRangedWeapon("doom_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SLAYER_CROSSBOW = registerRangedWeapon("slayer_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> SOUL_CROSSBOW = registerRangedWeapon("soul_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FERAL_SOUL_CROSSBOW = registerRangedWeapon("feral_soul_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> VOIDCALLER = registerRangedWeapon("voidcaller",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> SCATTER_CROSSBOW = registerRangedWeapon("scatter_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> HARP_CROSSBOW = registerRangedWeapon("harp_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> LIGHTNING_HARP_CROSSBOW = registerRangedWeapon("lightning_harp_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> EXPLODING_CROSSBOW = registerRangedWeapon("exploding_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> FIREBOLT_THROWER = registerRangedWeapon("firebolt_thrower",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> IMPLODING_CROSSBOW = registerRangedWeapon("imploding_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> BURST_CROSSBOW = registerRangedWeapon("burst_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> CORRUPTED_CROSSBOW = registerRangedWeapon("corrupted_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> SOUL_HUNTER_CROSSBOW = registerRangedWeapon("soul_hunter_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> DUAL_CROSSBOW = registerRangedWeapon("dual_crossbow",
            () -> new DualCrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> BABY_CROSSBOW = registerRangedWeapon("baby_crossbow",
            () -> new DualCrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> HARPOON_CROSSBOW = registerRangedWeapon("harpoon_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));
    public static final RegistryObject<Item> NAUTICAL_CROSSBOW = registerRangedWeapon("nautical_crossbow",
            () -> new CrossbowGear(RANGED_WEAPON_PROPERTIES));

    public static final RegistryObject<Item> BOOTS_OF_SWIFTNESS = registerArtifact("boots_of_swiftness",
            () -> new BootsOfSwiftnessItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> DEATH_CAP_MUSHROOM = registerArtifact("death_cap_mushroom",
            () -> new DeathCapMushroomItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> GOLEM_KIT = registerArtifact("golem_kit",
            () -> new GolemKitItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> TASTY_BONE = registerArtifact("tasty_bone",
            () -> new TastyBoneItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> WONDERFUL_WHEAT = registerArtifact("wonderful_wheat",
            () -> new WonderfulWheatItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> GONG_OF_WEAKENING = registerArtifact("gong_of_weakening",
            () -> new GongOfWeakeningItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> LIGHTNING_ROD = registerArtifact("lightning_rod",
            () -> new LightningRodItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> IRON_HIDE_AMULET = registerArtifact("iron_hide_amulet",
            () -> new IronHideAmuletItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> LOVE_MEDALLION = registerArtifact("love_medallion",
            () -> new LoveMedallionItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> GHOST_CLOAK = registerArtifact("ghost_cloak",
            () -> new GhostCloakItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> HARVESTER = registerArtifact("harvester",
            () -> new HarvesterItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> SHOCK_POWDER = registerArtifact("shock_powder",
            () -> new ShockPowderItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> CORRUPTED_SEEDS = registerArtifact("corrupted_seeds",
            () -> new CorruptedSeedsItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> ICE_WAND = registerArtifact("ice_wand",
            () -> new IceWandItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> WIND_HORN = registerArtifact("wind_horn",
            () -> new WindHornItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> SOUL_HEALER = registerArtifact("soul_healer",
            () -> new SoulHealerItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> LIGHT_FEATHER = registerArtifact("light_feather",
            () -> new LightFeatherItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> FLAMING_QUIVER = registerArtifact("flaming_quiver",
            () -> new FlamingQuiverItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> TORMENT_QUIVER = registerArtifact("torment_quiver",
            () -> new TormentQuiverItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> TOTEM_OF_REGENERATION = registerArtifact("totem_of_regeneration",
            () -> new TotemOfRegenerationItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> TOTEM_OF_SHIELDING = registerArtifact("totem_of_shielding",
            () -> new TotemOfShieldingItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> TOTEM_OF_SOUL_PROTECTION = registerArtifact("totem_of_soul_protection",
            () -> new TotemOfSoulProtectionItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> CORRUPTED_BEACON = registerArtifact("corrupted_beacon",
            () -> new CorruptedBeaconItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> BUZZY_NEST = registerArtifact("buzzy_nest",
            () -> new BuzzyNestItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> ENCHANTED_GRASS = registerArtifact("enchanted_grass",
            () -> new EnchantedGrassItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> CORRUPTED_PUMPKIN = registerArtifact("corrupted_pumpkin",
            () -> new CorruptedPumpkinItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> THUNDERING_QUIVER = registerArtifact("thundering_quiver",
            () -> new ThunderingQuiverItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> HARPOON_QUIVER = registerArtifact("harpoon_quiver",
            () -> new HarpoonQuiverItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> SATCHEL_OF_ELIXIRS = registerArtifact("satchel_of_elixirs",
            () -> new SatchelOfElixirsItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> SATCHEL_OF_SNACKS = registerArtifact("satchel_of_snacks",
            () -> new SatchelOfSnacksItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> SATCHEL_OF_ELEMENTS = registerArtifact("satchel_of_elements",
            () -> new SatchelOfElementsItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> POWERSHAKER = registerArtifact("powershaker",
            () -> new PowershakerItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> UPDRAFT_TOME = registerArtifact("updraft_tome",
            () -> new UpdraftTomeItem(ARTIFACT_PROPERTIES));
    public static final RegistryObject<Item> EYE_OF_THE_GUARDIAN = registerArtifact("eye_of_the_guardian",
            () -> new EyeOfTheGuardianItem(ARTIFACT_PROPERTIES));

    public static final RegistryObject<Item> HUNTERS_ARMOR = registerArmor("hunters_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/hunters_armor.png")));
    public static final RegistryObject<Item> ARCHERS_ARMOR = registerArmor("archers_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/archers_armor.png")));
    public static final RegistryObject<Item> ARCHERS_ARMOR_HOOD = registerArmor("archers_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/archers_armor.png")));

    public static final RegistryObject<Item> BATTLE_ROBE = registerArmor("battle_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/battle_robe.png")));
    public static final RegistryObject<Item> SPLENDID_ROBE = registerArmor("splendid_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/splendid_robe.png")));

    public static final RegistryObject<Item> CHAMPIONS_ARMOR = registerArmor("champions_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/champions_armor.png")));
    public static final RegistryObject<Item> CHAMPIONS_ARMOR_HELMET = registerArmor("champions_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/champions_armor.png")));
    public static final RegistryObject<Item> HEROS_ARMOR = registerArmor("heros_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/heros_armor.png")));
    public static final RegistryObject<Item> HEROS_ARMOR_HELMET = registerArmor("heros_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/heros_armor.png")));

    public static final RegistryObject<Item> DARK_ARMOR = registerArmor("dark_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/dark_armor.png")));
    public static final RegistryObject<Item> DARK_ARMOR_HELMET = registerArmor("dark_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/dark_armor.png")));
    public static final RegistryObject<Item> ROYAL_GUARD_ARMOR = registerArmor("royal_guard_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/royal_guard_armor.png")));
    public static final RegistryObject<Item> ROYAL_GUARD_ARMOR_HELMET = registerArmor("royal_guard_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/royal_guard_armor.png")));
    public static final RegistryObject<Item> TITANS_SHROUD = registerArmor("titans_shroud_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/titans_shroud.png")));
    public static final RegistryObject<Item> TITANS_SHROUD_HELMET = registerArmor("titans_shroud_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/titans_shroud.png")));

    public static final RegistryObject<Item> EVOCATION_ROBE = registerArmor("evocation_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/evocation_robe.png")));
    public static final RegistryObject<Item> EVOCATION_ROBE_HAT = registerArmor("evocation_hat",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/evocation_robe.png")));
    public static final RegistryObject<Item> EMBER_ROBE = registerArmor("ember_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/ember_robe.png")));
    public static final RegistryObject<Item> EMBER_ROBE_HAT = registerArmor("ember_hat",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/ember_robe.png")));

    public static final RegistryObject<Item> GRIM_ARMOR = registerArmor("grim_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/grim_armor.png")));
    public static final RegistryObject<Item> GRIM_ARMOR_HELMET = registerArmor("grim_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/grim_armor.png")));
    public static final RegistryObject<Item> WITHER_ARMOR = registerArmor("wither_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/wither_armor.png")));
    public static final RegistryObject<Item> WITHER_ARMOR_HELMET = registerArmor("wither_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/wither_armor.png")));

    public static final RegistryObject<Item> GUARDS_ARMOR = registerArmor("guards_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/guards_armor.png")));
    public static final RegistryObject<Item> GUARDS_ARMOR_HELMET = registerArmor("guards_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/guards_armor.png")));
    public static final RegistryObject<Item> CURIOUS_ARMOR = registerArmor("curious_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/curious_armor.png")));
    public static final RegistryObject<Item> CURIOUS_ARMOR_HELMET = registerArmor("curious_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/curious_armor.png")));

    public static final RegistryObject<Item> MERCENARY_ARMOR = registerArmor("mercenary_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/mercenary_armor.png")));
    public static final RegistryObject<Item> MERCENARY_ARMOR_HELMET = registerArmor("mercenary_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/mercenary_armor.png")));
    public static final RegistryObject<Item> RENEGADE_ARMOR = registerArmor("renegade_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/renegade_armor.png")));
    public static final RegistryObject<Item> RENEGADE_ARMOR_HELMET = registerArmor("renegade_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/renegade_armor.png")));
    public static final RegistryObject<Item> HUNGRY_HORROR_CHESTPLATE = registerArmor("hungry_horror_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/hungry_horror_armor.png")));
    public static final RegistryObject<Item> HUNGRY_HORROR_HELMET = registerArmor("hungry_horror_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/hungry_horror_armor.png")));

    public static final RegistryObject<Item> OCELOT_ARMOR = registerArmor("ocelot_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/ocelot_armor.png")));
    public static final RegistryObject<Item> OCELOT_ARMOR_HOOD = registerArmor("ocelot_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/ocelot_armor.png")));
    public static final RegistryObject<Item> SHADOW_WALKER = registerArmor("shadow_walker_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/shadow_walker.png")));
    public static final RegistryObject<Item> SHADOW_WALKER_HOOD = registerArmor("shadow_walker_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/shadow_walker.png")));

    public static final RegistryObject<Item> PHANTOM_ARMOR = registerArmor("phantom_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/phantom_armor.png")));
    public static final RegistryObject<Item> PHANTOM_ARMOR_HELMET = registerArmor("phantom_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/phantom_armor.png")));
    public static final RegistryObject<Item> FROST_BITE = registerArmor("frost_bite_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/frost_bite.png")));
    public static final RegistryObject<Item> FROST_BITE_HELMET = registerArmor("frost_bite_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/frost_bite.png")));

    public static final RegistryObject<Item> PLATE_ARMOR = registerArmor("plate_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/plate_armor.png")));
    public static final RegistryObject<Item> PLATE_ARMOR_HELMET = registerArmor("plate_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/plate_armor.png")));
    public static final RegistryObject<Item> FULL_METAL_ARMOR = registerArmor("full_metal_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/full_metal_armor.png")));
    public static final RegistryObject<Item> FULL_METAL_ARMOR_HELMET = registerArmor("full_metal_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/full_metal_armor.png")));

    public static final RegistryObject<Item> REINFORCED_MAIL = registerArmor("reinforced_mail_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/reinforced_mail.png")));
    public static final RegistryObject<Item> REINFORCED_MAIL_HELMET = registerArmor("reinforced_mail_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/reinforced_mail.png")));
    public static final RegistryObject<Item> STALWART_ARMOR = registerArmor("stalwart_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/stalwart_armor.png")));
    public static final RegistryObject<Item> STALWART_ARMOR_HELMET = registerArmor("stalwart_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/stalwart_armor.png")));

    public static final RegistryObject<Item> SCALE_MAIL = registerArmor("scale_mail_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/scale_mail.png")));
    public static final RegistryObject<Item> HIGHLAND_ARMOR = registerArmor("highland_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/highland_armor.png")));
    public static final RegistryObject<Item> HIGHLAND_ARMOR_HELMET = registerArmor("highland_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/highland_armor.png")));

    public static final RegistryObject<Item> SNOW_ARMOR = registerArmor("snow_chestplate",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/snow_armor.png")));
    public static final RegistryObject<Item> SNOW_ARMOR_HELMET = registerArmor("snow_helmet",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/snow_armor.png")));
    public static final RegistryObject<Item> FROST_ARMOR = registerArmor("frost_chestplate",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/frost_armor.png")));
    public static final RegistryObject<Item> FROST_ARMOR_HELMET = registerArmor("frost_helmet",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/frost_armor.png")));

    public static final RegistryObject<Item> SOUL_ROBE = registerArmor("soul_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/soul_robe.png")));
    public static final RegistryObject<Item> SOUL_ROBE_HOOD = registerArmor("soul_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/soul_robe.png")));
    public static final RegistryObject<Item> SOULDANCER_ROBE = registerArmor("souldancer_robe",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/souldancer_robe.png")));
    public static final RegistryObject<Item> SOULDANCER_ROBE_HOOD = registerArmor("souldancer_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/souldancer_robe.png")));

    public static final RegistryObject<Item> SPELUNKER_ARMOR = registerArmor("spelunker_chestplate",
            () -> new PetBatArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/spelunker_armor.png")));
    public static final RegistryObject<Item> SPELUNKER_ARMOR_HELMET = registerArmor("spelunker_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/spelunker_armor.png")));
    public static final RegistryObject<Item> CAVE_CRAWLER = registerArmor("cave_crawler_chestplate",
            () -> new PetBatArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/cave_crawler.png")));
    public static final RegistryObject<Item> CAVE_CRAWLER_HELMET = registerArmor("cave_crawler_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/cave_crawler.png")));

    public static final RegistryObject<Item> THIEF_ARMOR = registerArmor("thief_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/thief_armor.png")));
    public static final RegistryObject<Item> THIEF_ARMOR_HOOD = registerArmor("thief_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/thief_armor.png")));
    public static final RegistryObject<Item> SPIDER_ARMOR = registerArmor("spider_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/spider_armor.png")));
    public static final RegistryObject<Item> SPIDER_ARMOR_HOOD = registerArmor("spider_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/spider_armor.png")));

    public static final RegistryObject<Item> WOLF_ARMOR = registerArmor("wolf_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/wolf_armor.png")));
    public static final RegistryObject<Item> WOLF_ARMOR_HOOD = registerArmor("wolf_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/wolf_armor.png")));
    public static final RegistryObject<Item> FOX_ARMOR = registerArmor("fox_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/fox_armor.png")));
    public static final RegistryObject<Item> FOX_ARMOR_HOOD = registerArmor("fox_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/fox_armor.png")));
    public static final RegistryObject<Item> ARCTIC_FOX_VEST = registerArmor("arctic_fox_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/arctic_fox_armor.png")));
    public static final RegistryObject<Item> ARCTIC_FOX_HOOD = registerArmor("arctic_fox_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/arctic_fox_armor.png")));

    public static final RegistryObject<Item> CLIMBING_GEAR = registerArmor("climbing_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/climbing_gear.png")));
    public static final RegistryObject<Item> CLIMBING_GEAR_HOOD = registerArmor("climbing_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/climbing_gear.png")));
    public static final RegistryObject<Item> RUGGED_CLIMBING_GEAR = registerArmor("rugged_climbing_vest",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/rugged_climbing_gear.png")));
    public static final RegistryObject<Item> RUGGED_CLIMBING_GEAR_HOOD = registerArmor("rugged_climbing_hood",
            () -> new FreezingResistanceArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/rugged_climbing_gear.png")));
    public static final RegistryObject<Item> GOAT_GEAR = registerArmor("goat_vest",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/goat_gear.png")));
    public static final RegistryObject<Item> GOAT_GEAR_HOOD = registerArmor("goat_hood",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/goat_gear.png")));

    public static final RegistryObject<Item> EMERALD_GEAR = registerArmor("emerald_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/emerald_gear.png")));
    public static final RegistryObject<Item> EMERALD_GEAR_HELMET = registerArmor("emerald_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/emerald_gear.png")));
    public static final RegistryObject<Item> GILDED_GLORY = registerArmor("gilded_glory_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/gilded_glory.png")));
    public static final RegistryObject<Item> GILDED_GLORY_HELMET = registerArmor("gilded_glory_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/gilded_glory.png")));
    public static final RegistryObject<Item> OPULENT_ARMOR = registerArmor("opulent_chestplate",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/opulent_armor.png")));
    public static final RegistryObject<Item> OPULENT_ARMOR_HELMET = registerArmor("opulent_helmet",
            () -> new ArmorGear(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, ARMOR_PROPERTIES, new ResourceLocation(MODID, "textures/models/armor/opulent_armor.png")));

    public static final RegistryObject<Item> ARROW_BUNDLE = ITEMS.register("arrow_bundle",
            () -> new ArrowBundleItem(new Item.Properties().tab(RANGED_WEAPON_GROUP)));

    private static RegistryObject<Item> registerArmor(String armorId, Supplier<Item> itemSupplier) {
        RegistryObject<Item> register = ITEMS.register(armorId, itemSupplier);
        ARMORS.put(modLoc(armorId), register);
        return register;
    }

    private static RegistryObject<Item> registerMeleeWeapon(String meleeWeaponId, Supplier<Item> itemSupplier) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        MELEE_WEAPONS.put(modLoc(meleeWeaponId), register);
        return register;
    }

    private static RegistryObject<Item> registerRangedWeapon(String meleeWeaponId, Supplier<Item> itemSupplier) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        RANGED_WEAPONS.put(modLoc(meleeWeaponId), register);
        return register;
    }

    private static RegistryObject<Item> registerArtifact(String meleeWeaponId, Supplier<Item> itemSupplier) {
        RegistryObject<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
        ARTIFACTS.put(modLoc(meleeWeaponId), register);
        return register;
    }

    private static ResourceLocation modLoc(String armorId) {
        return new ResourceLocation(MODID, armorId);
    }

}
