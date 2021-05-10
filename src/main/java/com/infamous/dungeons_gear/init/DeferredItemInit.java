package com.infamous.dungeons_gear.init;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.*;
import com.infamous.dungeons_gear.artifacts.*;
import com.infamous.dungeons_gear.artifacts.beacon.CorruptedBeaconItem;
import com.infamous.dungeons_gear.artifacts.beacon.CorruptedPumpkinItem;
import com.infamous.dungeons_gear.items.ArmorMaterialList;
import com.infamous.dungeons_gear.items.ToolMaterialList;
import com.infamous.dungeons_gear.melee.*;
import com.infamous.dungeons_gear.ranged.bows.*;
import com.infamous.dungeons_gear.ranged.crossbows.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class DeferredItemInit {

    public static Map<Item, ResourceLocation> commonLeatherArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> commonMetalArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> uniqueLeatherArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> uniqueMetalArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> artifactMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> uniqueRangedWeaponMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> commonRangedWeaponMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> uniqueWeaponMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> commonWeaponMap = new HashMap<Item, ResourceLocation>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> DAGGER = ITEMS.register("dagger",
            () -> new DaggerItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FANG_OF_FROST = ITEMS.register("fang_of_frost",
            () -> new DaggerItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> MOON_DAGGER = ITEMS.register("moon_dagger",
            () -> new DaggerItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> SHEAR_DAGGER = ITEMS.register("shear_dagger",
            () -> new DaggerItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> SICKLE = ITEMS.register("sickle",
            () -> new SickleItem(ToolMaterialList.METAL, 3, (2.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> NIGHTMARES_BITE = ITEMS.register("nightmares_bite",
            () -> new SickleItem(ToolMaterialList.METAL, 3, (2.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> THE_LAST_LAUGH = ITEMS.register("the_last_laugh",
            () -> new SickleItem(ToolMaterialList.METAL, 3, (2.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> GAUNTLET = ITEMS.register("gauntlet",
            () -> new GauntletItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FIGHTERS_BINDING = ITEMS.register("fighters_binding",
            () -> new GauntletItem(ToolMaterialList.METAL, 2, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> MAULER = ITEMS.register("mauler",
            () -> new GauntletItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> SOUL_FIST = ITEMS.register("soul_fist",
            () -> new GauntletItem(ToolMaterialList.METAL, 2, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> RAPIER = ITEMS.register("rapier",
            () -> new RapierItem(ToolMaterialList.METAL, 2, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> BEE_STINGER = ITEMS.register("bee_stinger",
            () -> new RapierItem(ToolMaterialList.METAL, 2, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> FREEZING_FOIL = ITEMS.register("freezing_foil",
            () -> new RapierItem(ToolMaterialList.METAL, 2, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> SOUL_SCYTHE = ITEMS.register("soul_scythe",
            () -> new SoulScytheItem(ToolMaterialList.METAL, 6, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FROST_SCYTHE = ITEMS.register("frost_scythe",
            () -> new SoulScytheItem(ToolMaterialList.METAL, 6, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> JAILORS_SCYTHE = ITEMS.register("jailors_scythe",
            () -> new SoulScytheItem(ToolMaterialList.METAL, 6, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> CUTLASS = ITEMS.register("cutlass",
            () -> new CutlassItem(ToolMaterialList.METAL, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> DANCERS_SWORD = ITEMS.register("dancers_sword",
            () -> new CutlassItem(ToolMaterialList.METAL, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> NAMELESS_BLADE = ITEMS.register("nameless_blade",
            () -> new CutlassItem(ToolMaterialList.METAL, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> SWORD = ITEMS.register("sword",
            () -> new DungeonsSwordItem(ToolMaterialList.METAL, 5, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> DIAMOND_SWORD = ITEMS.register("diamond_sword",
            () -> new DungeonsSwordItem(ToolMaterialList.DIAMOND, 5, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> HAWKBRAND = ITEMS.register("hawkbrand",
            () -> new DungeonsSwordItem(ToolMaterialList.METAL, 5, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> SINISTER_SWORD = ITEMS.register("sinister_sword",
            () -> new DungeonsSwordItem(ToolMaterialList.METAL, 5, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> PICKAXE = ITEMS.register("pickaxe",
            () -> new DungeonsPickaxeItem(ToolMaterialList.METAL, 3, (1.2f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> DIAMOND_PICKAXE = ITEMS.register("diamond_pickaxe",
            () -> new DungeonsPickaxeItem(ToolMaterialList.DIAMOND, 3, (1.2f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> BATTLESTAFF = ITEMS.register("battlestaff",
            () -> new StaffItem(ToolMaterialList.METAL, 5, (1.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> BATTLESTAFF_OF_TERROR = ITEMS.register("battlestaff_of_terror",
            () -> new StaffItem(ToolMaterialList.METAL, 5, (1.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> GROWING_STAFF = ITEMS.register("growing_staff",
            () -> new StaffItem(ToolMaterialList.METAL, 5, (1.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> AXE = ITEMS.register("axe",
            () -> new DungeonsAxeItem(ToolMaterialList.METAL, 8, (1.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FIREBRAND = ITEMS.register("firebrand",
            () -> new DungeonsAxeItem(ToolMaterialList.METAL, 8, (1.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> HIGHLAND_AXE = ITEMS.register("highland_axe",
            () -> new DungeonsAxeItem(ToolMaterialList.METAL, 8, (1.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> DOUBLE_AXE = ITEMS.register("double_axe",
            () -> new DoubleAxeItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> CURSED_AXE = ITEMS.register("cursed_axe",
            () -> new DoubleAxeItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> WHIRLWIND = ITEMS.register("whirlwind",
            () -> new DoubleAxeItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> MACE = ITEMS.register("mace",
            () -> new MaceItem(ToolMaterialList.METAL, 6, (1.3f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FLAIL = ITEMS.register("flail",
            () -> new MaceItem(ToolMaterialList.METAL, 6, (1.3f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> SUNS_GRACE = ITEMS.register("suns_grace",
            () -> new MaceItem(ToolMaterialList.METAL, 6, (1.3f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> GREAT_HAMMER = ITEMS.register("great_hammer",
            () -> new GreatHammerItem(ToolMaterialList.METAL, 8, (1.1f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> HAMMER_OF_GRAVITY = ITEMS.register("hammer_of_gravity",
            () -> new GreatHammerItem(ToolMaterialList.METAL, 8, (1.1f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> STORMLANDER = ITEMS.register("stormlander",
            () -> new GreatHammerItem(ToolMaterialList.METAL, 8, (1.1f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new KatanaItem(ToolMaterialList.METAL, 8, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> DARK_KATANA = ITEMS.register("dark_katana",
            () -> new KatanaItem(ToolMaterialList.METAL, 8, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> MASTERS_KATANA = ITEMS.register("masters_katana",
            () -> new KatanaItem(ToolMaterialList.METAL, 8, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> SOUL_KNIFE = ITEMS.register("soul_knife",
            () -> new SoulKnifeItem(ToolMaterialList.METAL, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> ETERNAL_KNIFE = ITEMS.register("eternal_knife",
            () -> new SoulKnifeItem(ToolMaterialList.METAL, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> TRUTHSEEKER = ITEMS.register("truthseeker",
            () -> new SoulKnifeItem(ToolMaterialList.METAL, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> CLAYMORE = ITEMS.register("claymore",
            () -> new ClaymoreItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> BROADSWORD = ITEMS.register("broadsword",
            () -> new ClaymoreItem(ToolMaterialList.METAL, 11, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> HEARTSTEALER = ITEMS.register("heartstealer",
            () -> new ClaymoreItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> GREAT_AXEBLADE = ITEMS.register("great_axeblade",
            () -> new ClaymoreItem(ToolMaterialList.METAL, 10, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> SPEAR = ITEMS.register("spear",
            () -> new SpearItem(ToolMaterialList.METAL, 5, (1.4f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> FORTUNE_SPEAR = ITEMS.register("fortune_spear",
            () -> new SpearItem(ToolMaterialList.METAL, 5, (1.4f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> WHISPERING_SPEAR = ITEMS.register("whispering_spear",
            () -> new SpearItem(ToolMaterialList.METAL, 5, (1.4f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> GLAIVE = ITEMS.register("glaive",
            () -> new GlaiveItem(ToolMaterialList.METAL, 7, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> GRAVE_BANE = ITEMS.register("grave_bane",
            () -> new GlaiveItem(ToolMaterialList.METAL, 7, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));
    public static final RegistryObject<Item> VENOM_GLAIVE = ITEMS.register("venom_glaive",
            () -> new GlaiveItem(ToolMaterialList.METAL, 7, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> WHIP = ITEMS.register("whip",
            () -> new WhipItem(ToolMaterialList.METAL, 8, (1.0f-4.0f), 2.5F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), false));
    public static final RegistryObject<Item> VINE_WHIP = ITEMS.register("vine_whip",
            () -> new WhipItem(ToolMaterialList.METAL, 8, (1.0f-4.0f), 2.5F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP), true));

    public static final RegistryObject<Item> BONEBOW = ITEMS.register("bonebow",
            () -> new DungeonsBowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> TWIN_BOW = ITEMS.register("twin_bow",
            () -> new DungeonsBowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> HAUNTED_BOW = ITEMS.register("haunted_bow",
            () -> new DungeonsBowItem(new Item.Properties(), 20.0F, true));

    public static final RegistryObject<Item> SOUL_BOW = ITEMS.register("soul_bow",
            () -> new SoulBowItem(new Item.Properties(), 20.0F, false));
    public static final RegistryObject<Item> BOW_OF_LOST_SOULS = ITEMS.register("bow_of_lost_souls",
            () -> new SoulBowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> NOCTURNAL_BOW = ITEMS.register("nocturnal_bow",
            () -> new SoulBowItem(new Item.Properties(), 20.0F, true));

    public static final RegistryObject<Item> POWER_BOW = ITEMS.register("power_bow",
            () -> new PowerBowItem(new Item.Properties(), 25.0F, false));
    public static final RegistryObject<Item> ELITE_POWER_BOW = ITEMS.register("elite_power_bow",
            () -> new PowerBowItem(new Item.Properties(), 25.0F, true));
    public static final RegistryObject<Item> SABREWING = ITEMS.register("sabrewing",
            () -> new PowerBowItem(new Item.Properties(), 25.0F, true));

    public static final RegistryObject<Item> LONGBOW = ITEMS.register("longbow",
            () -> new LongbowItem(new Item.Properties(), 25.0F, false));
    public static final RegistryObject<Item> GUARDIAN_BOW = ITEMS.register("guardian_bow",
            () -> new LongbowItem(new Item.Properties(), 25.0F, true));
    public static final RegistryObject<Item> RED_SNAKE = ITEMS.register("red_snake",
            () -> new LongbowItem(new Item.Properties(), 25.0F, true));

    public static final RegistryObject<Item> HUNTING_BOW = ITEMS.register("hunting_bow",
            () -> new HuntingBowItem(new Item.Properties(), 20.0F, false));
    public static final RegistryObject<Item> HUNTERS_PROMISE = ITEMS.register("hunters_promise",
            () -> new HuntingBowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> MASTERS_BOW = ITEMS.register("masters_bow",
            () -> new HuntingBowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> ANCIENT_BOW = ITEMS.register("ancient_bow",
            () -> new HuntingBowItem(new Item.Properties(), 20.0F, true));

    public static final RegistryObject<Item> SHORTBOW = ITEMS.register("shortbow",
            () -> new ShortbowItem(new Item.Properties(), 15.0F, false));
    public static final RegistryObject<Item> MECHANICAL_SHORTBOW = ITEMS.register("mechanical_shortbow",
            () -> new ShortbowItem(new Item.Properties(), 15.0F, true));
    public static final RegistryObject<Item> PURPLE_STORM = ITEMS.register("purple_storm",
            () -> new ShortbowItem(new Item.Properties(), 15.0F, true));
    public static final RegistryObject<Item> LOVE_SPELL_BOW = ITEMS.register("love_spell_bow",
            () -> new ShortbowItem(new Item.Properties(), 15.0F, true));

    public static final RegistryObject<Item> TRICKBOW = ITEMS.register("trickbow",
            () -> new TrickbowItem(new Item.Properties(), 20.0F, false));
    public static final RegistryObject<Item> THE_GREEN_MENACE = ITEMS.register("the_green_menace",
            () -> new TrickbowItem(new Item.Properties(), 20.0F, true));
    public static final RegistryObject<Item> THE_PINK_SCOUNDREL = ITEMS.register("the_pink_scoundrel",
            () -> new TrickbowItem(new Item.Properties(), 20.0F, true));

    public static final RegistryObject<Item> SNOW_BOW = ITEMS.register("snow_bow",
            () -> new SnowBowItem(new Item.Properties(), 20.0F, false));
    public static final RegistryObject<Item> WINTERS_TOUCH = ITEMS.register("winters_touch",
            () -> new SnowBowItem(new Item.Properties(), 20.0F, true));


    public static final RegistryObject<Item> RAPID_CROSSBOW = ITEMS.register("rapid_crossbow",
            () -> new RapidCrossbowItem(new Item.Properties(), 20, false));
    public static final RegistryObject<Item> BUTTERFLY_CROSSBOW = ITEMS.register("butterfly_crossbow",
            () -> new RapidCrossbowItem(new Item.Properties(), 20, true));
    public static final RegistryObject<Item> AUTO_CROSSBOW = ITEMS.register("auto_crossbow",
            () -> new RapidCrossbowItem(new Item.Properties(), 20, true));

    public static final RegistryObject<Item> AZURE_SEEKER = ITEMS.register("azure_seeker",
            () -> new DungeonsCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> THE_SLICER = ITEMS.register("the_slicer",
            () -> new DungeonsCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> HEAVY_CROSSBOW = ITEMS.register("heavy_crossbow",
            () -> new HeavyCrossbowItem(new Item.Properties(), 30, false));
    public static final RegistryObject<Item> DOOM_CROSSBOW = ITEMS.register("doom_crossbow",
            () -> new HeavyCrossbowItem(new Item.Properties(), 30, true));
    public static final RegistryObject<Item> SLAYER_CROSSBOW = ITEMS.register("slayer_crossbow",
            () -> new HeavyCrossbowItem(new Item.Properties(), 30, true));

    public static final RegistryObject<Item> SOUL_CROSSBOW = ITEMS.register("soul_crossbow",
            () -> new SoulCrossbowItem(new Item.Properties(), 25, false));
    public static final RegistryObject<Item> FERAL_SOUL_CROSSBOW = ITEMS.register("feral_soul_crossbow",
            () -> new SoulCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> VOIDCALLER = ITEMS.register("voidcaller",
            () -> new SoulCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> SCATTER_CROSSBOW = ITEMS.register("scatter_crossbow",
            () -> new ScatterCrossbowItem(new Item.Properties(), 25, false));
    public static final RegistryObject<Item> HARP_CROSSBOW = ITEMS.register("harp_crossbow",
            () -> new ScatterCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> LIGHTNING_HARP_CROSSBOW = ITEMS.register("lightning_harp_crossbow",
            () -> new ScatterCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> EXPLODING_CROSSBOW = ITEMS.register("exploding_crossbow",
            () -> new ExplodingCrossbowItem(new Item.Properties(), 25, false));
    public static final RegistryObject<Item> FIREBOLT_THROWER = ITEMS.register("firebolt_thrower",
            () -> new ExplodingCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> IMPLODING_CROSSBOW = ITEMS.register("imploding_crossbow",
            () -> new ExplodingCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> BURST_CROSSBOW = ITEMS.register("burst_crossbow",
            () -> new BurstCrossbowItem(new Item.Properties(), 25, false));
    public static final RegistryObject<Item> CORRUPTED_CROSSBOW = ITEMS.register("corrupted_crossbow",
            () -> new BurstCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> SOUL_HUNTER_CROSSBOW = ITEMS.register("soul_hunter_crossbow",
            () -> new BurstCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> DUAL_CROSSBOW = ITEMS.register("dual_crossbow",
            () -> new DualCrossbowItem(new Item.Properties(), 25, true));
    public static final RegistryObject<Item> BABY_CROSSBOW = ITEMS.register("baby_crossbow",
            () -> new DualCrossbowItem(new Item.Properties(), 25, true));

    public static final RegistryObject<Item> BOOTS_OF_SWIFTNESS = ITEMS.register("boots_of_swiftness",
            () -> new BootsOfSwiftnessItem(new Item.Properties()));
    public static final RegistryObject<Item> DEATH_CAP_MUSHROOM = ITEMS.register("death_cap_mushroom",
            () -> new DeathCapMushroomItem(new Item.Properties()));
    public static final RegistryObject<Item> GOLEM_KIT = ITEMS.register("golem_kit",
            () -> new GolemKitItem(new Item.Properties()));
    public static final RegistryObject<Item> TASTY_BONE = ITEMS.register("tasty_bone",
            () -> new TastyBoneItem(new Item.Properties()));
    public static final RegistryObject<Item> WONDERFUL_WHEAT = ITEMS.register("wonderful_wheat",
            () -> new WonderfulWheatItem(new Item.Properties()));
    public static final RegistryObject<Item> GONG_OF_WEAKENING = ITEMS.register("gong_of_weakening",
            () -> new GongOfWeakeningItem(new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_ROD = ITEMS.register("lightning_rod",
            () -> new LightningRodItem(new Item.Properties()));
    public static final RegistryObject<Item> IRON_HIDE_AMULET = ITEMS.register("iron_hide_amulet",
            () -> new IronHideAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> LOVE_MEDALLION = ITEMS.register("love_medallion",
            () -> new LoveMedallionItem(new Item.Properties()));
    public static final RegistryObject<Item> GHOST_CLOAK = ITEMS.register("ghost_cloak",
            () -> new GhostCloakItem(new Item.Properties()));
    public static final RegistryObject<Item> HARVESTER = ITEMS.register("harvester",
            () -> new HarvesterItem(new Item.Properties()));
    public static final RegistryObject<Item> SHOCK_POWDER = ITEMS.register("shock_powder",
            () -> new ShockPowderItem(new Item.Properties()));
    public static final RegistryObject<Item> CORRUPTED_SEEDS = ITEMS.register("corrupted_seeds",
            () -> new CorruptedSeedsItem(new Item.Properties()));
    public static final RegistryObject<Item> ICE_WAND = ITEMS.register("ice_wand",
            () -> new IceWandItem(new Item.Properties()));
    public static final RegistryObject<Item> WIND_HORN = ITEMS.register("wind_horn",
            () -> new WindHornItem(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_HEALER = ITEMS.register("soul_healer",
            () -> new SoulHealerItem(new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_FEATHER = ITEMS.register("light_feather",
            () -> new LightFeatherItem(new Item.Properties()));
    public static final RegistryObject<Item> FLAMING_QUIVER = ITEMS.register("flaming_quiver",
            () -> new FlamingQuiverItem(new Item.Properties()));
    public static final RegistryObject<Item> TORMENT_QUIVER = ITEMS.register("torment_quiver",
            () -> new TormentQuiver(new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_REGENERATION = ITEMS.register("totem_of_regeneration",
            () -> new TotemOfRegenerationItem(new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_SHIELDING = ITEMS.register("totem_of_shielding",
            () -> new TotemOfShieldingItem(new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_SOUL_PROTECTION = ITEMS.register("totem_of_soul_protection",
            () -> new TotemOfSoulProtectionItem(new Item.Properties()));
    public static final RegistryObject<Item> CORRUPTED_BEACON = ITEMS.register("corrupted_beacon",
            () -> new CorruptedBeaconItem(new Item.Properties()));
    public static final RegistryObject<Item> BUZZY_NEST = ITEMS.register("buzzy_nest",
            () -> new BuzzyNestItem(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_GRASS = ITEMS.register("enchanted_grass",
            () -> new EnchantedGrassItem(new Item.Properties()));
    public static final RegistryObject<Item> CORRUPTED_PUMPKIN = ITEMS.register("corrupted_pumpkin",
            () -> new CorruptedPumpkinItem(new Item.Properties()));

    public static final RegistryObject<Item> HUNTERS_ARMOR = ITEMS.register("hunters_vest",
            () -> new HuntersArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> ARCHERS_ARMOR = ITEMS.register("archers_vest",
            () -> new HuntersArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> ARCHERS_ARMOR_HOOD = ITEMS.register("archers_hood",
            () ->  new HuntersArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> BATTLE_ROBE = ITEMS.register("battle_robe",
            () -> new BattleRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SPLENDID_ROBE = ITEMS.register("splendid_robe",
            () -> new BattleRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> CHAMPIONS_ARMOR = ITEMS.register("champions_chestplate",
            () -> new ChampionsArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> CHAMPIONS_ARMOR_HELMET = ITEMS.register("champions_helmet",
            () -> new ChampionsArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> HEROS_ARMOR = ITEMS.register("heros_chestplate",
            () -> new ChampionsArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> HEROS_ARMOR_HELMET = ITEMS.register("heros_helmet",
            () -> new ChampionsArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> DARK_ARMOR = ITEMS.register("dark_chestplate",
            () -> new DarkArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> DARK_ARMOR_HELMET = ITEMS.register("dark_helmet",
            () -> new DarkArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> ROYAL_GUARD_ARMOR = ITEMS.register("royal_guard_chestplate",
            () -> new RoyalGuardArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> ROYAL_GUARD_ARMOR_HELMET = ITEMS.register("royal_guard_helmet",
            () -> new RoyalGuardArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> TITANS_SHROUD = ITEMS.register("titans_shroud_chestplate",
            () -> new DarkArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> TITANS_SHROUD_HELMET = ITEMS.register("titans_shroud_helmet",
            () -> new DarkArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> EVOCATION_ROBE = ITEMS.register("evocation_robe",
            () -> new EvocationRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> EVOCATION_ROBE_HAT = ITEMS.register("evocation_hat",
            () -> new EvocationRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> EMBER_ROBE = ITEMS.register("ember_robe",
            () -> new EvocationRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> EMBER_ROBE_HAT = ITEMS.register("ember_hat",
            () -> new EvocationRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> GRIM_ARMOR = ITEMS.register("grim_chestplate",
            () -> new GrimArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> GRIM_ARMOR_HELMET = ITEMS.register("grim_helmet",
            () -> new GrimArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> WITHER_ARMOR = ITEMS.register("wither_chestplate",
            () -> new GrimArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> WITHER_ARMOR_HELMET = ITEMS.register("wither_helmet",
            () -> new GrimArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> GUARDS_ARMOR = ITEMS.register("guards_chestplate",
            () -> new GuardsArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> GUARDS_ARMOR_HELMET = ITEMS.register("guards_helmet",
            () -> new GuardsArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> CURIOUS_ARMOR = ITEMS.register("curious_chestplate",
            () -> new GuardsArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> CURIOUS_ARMOR_HELMET = ITEMS.register("curious_helmet",
            () -> new GuardsArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> MERCENARY_ARMOR = ITEMS.register("mercenary_chestplate",
            () -> new MercenaryArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> MERCENARY_ARMOR_HELMET = ITEMS.register("mercenary_helmet",
            () -> new MercenaryArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> RENEGADE_ARMOR = ITEMS.register("renegade_chestplate",
            () -> new MercenaryArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> RENEGADE_ARMOR_HELMET = ITEMS.register("renegade_helmet",
            () -> new MercenaryArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> OCELOT_ARMOR = ITEMS.register("ocelot_vest",
            () -> new OcelotArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> OCELOT_ARMOR_HOOD = ITEMS.register("ocelot_hood",
            () -> new OcelotArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SHADOW_WALKER = ITEMS.register("shadow_walker_vest",
            () -> new OcelotArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> SHADOW_WALKER_HOOD = ITEMS.register("shadow_walker_hood",
            () -> new OcelotArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> PHANTOM_ARMOR = ITEMS.register("phantom_chestplate",
            () -> new PhantomArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> PHANTOM_ARMOR_HELMET = ITEMS.register("phantom_helmet",
            () -> new PhantomArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> FROST_BITE = ITEMS.register("frost_bite_chestplate",
            () -> new PhantomArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> FROST_BITE_HELMET = ITEMS.register("frost_bite_helmet",
            () -> new PhantomArmorItem(ArmorMaterialList.BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> PLATE_ARMOR = ITEMS.register("plate_chestplate",
            () -> new PlateArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> PLATE_ARMOR_HELMET = ITEMS.register("plate_helmet",
            () -> new PlateArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> FULL_METAL_ARMOR = ITEMS.register("full_metal_chestplate",
            () -> new PlateArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> FULL_METAL_ARMOR_HELMET = ITEMS.register("full_metal_helmet",
            () -> new PlateArmorItem(ArmorMaterialList.HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> REINFORCED_MAIL = ITEMS.register("reinforced_mail_chestplate",
            () -> new ReinforcedMailItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> REINFORCED_MAIL_HELMET = ITEMS.register("reinforced_mail_helmet",
            () -> new ReinforcedMailItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> STALWART_ARMOR = ITEMS.register("stalwart_chestplate",
            () -> new ReinforcedMailItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> STALWART_ARMOR_HELMET = ITEMS.register("stalwart_helmet",
            () -> new ReinforcedMailItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> SCALE_MAIL = ITEMS.register("scale_mail_chestplate",
            () -> new ScaleMailItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> HIGHLAND_ARMOR = ITEMS.register("highland_chestplate",
            () -> new ScaleMailItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> HIGHLAND_ARMOR_HELMET = ITEMS.register("highland_helmet",
            () -> new ScaleMailItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> SNOW_ARMOR = ITEMS.register("snow_chestplate",
            () -> new SnowArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SNOW_ARMOR_HELMET = ITEMS.register("snow_helmet",
            () -> new SnowArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> FROST_ARMOR = ITEMS.register("frost_chestplate",
            () -> new SnowArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> FROST_ARMOR_HELMET = ITEMS.register("frost_helmet",
            () -> new SnowArmorItem(ArmorMaterialList.MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> SOUL_ROBE = ITEMS.register("soul_robe",
            () -> new SoulRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SOUL_ROBE_HOOD = ITEMS.register("soul_hood",
            () -> new SoulRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SOULDANCER_ROBE = ITEMS.register("souldancer_robe",
            () -> new SoulRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> SOULDANCER_ROBE_HOOD = ITEMS.register("souldancer_hood",
            () -> new SoulRobeItem(ArmorMaterialList.ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> SPELUNKER_ARMOR = ITEMS.register("spelunker_chestplate",
            () -> new SpelunkerArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SPELUNKER_ARMOR_HELMET = ITEMS.register("spelunker_helmet",
            () -> new SpelunkerArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> CAVE_CRAWLER = ITEMS.register("cave_crawler_chestplate",
            () -> new SpelunkerArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> CAVE_CRAWLER_HELMET = ITEMS.register("cave_crawler_helmet",
            () -> new SpelunkerArmorItem(ArmorMaterialList.LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> THIEF_ARMOR = ITEMS.register("thief_vest",
            () -> new ThiefArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> THIEF_ARMOR_HOOD = ITEMS.register("thief_hood",
            () -> new ThiefArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> SPIDER_ARMOR = ITEMS.register("spider_vest",
            () -> new ThiefArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> SPIDER_ARMOR_HOOD = ITEMS.register("spider_hood",
            () -> new ThiefArmorItem(ArmorMaterialList.VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static final RegistryObject<Item> WOLF_ARMOR = ITEMS.register("wolf_vest",
            () -> new WolfArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> WOLF_ARMOR_HOOD = ITEMS.register("wolf_hood",
            () -> new WolfArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false));
    public static final RegistryObject<Item> FOX_ARMOR = ITEMS.register("fox_vest",
            () -> new WolfArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));
    public static final RegistryObject<Item> FOX_ARMOR_HOOD = ITEMS.register("fox_hood",
            () -> new WolfArmorItem(ArmorMaterialList.PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true));

    public static void putItemsInMap() {
        putArtifactsInMap();
        putUniqueMeleeWeaponsInMap();
        putCommonMeleeWeaponsInMap();
        putUniqueBowsInMap();
        putUniqueCrossbowsInMap();
        putCommonBowsInMap();
        putCommonCrossbowsInMap();
        putCommonLeatherArmorsInMap();
        putUniqueLeatherArmorsInMap();
        putCommonMetalArmorsInMap();
        putUniqueMetalArmorsInMap();
    }

    private static void putUniqueMetalArmorsInMap() {
        uniqueMetalArmorMap.put(CAVE_CRAWLER.get(), CAVE_CRAWLER.get().getRegistryName());
        uniqueMetalArmorMap.put(CAVE_CRAWLER_HELMET.get(), CAVE_CRAWLER_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(HIGHLAND_ARMOR.get(), HIGHLAND_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(HIGHLAND_ARMOR_HELMET.get(), HIGHLAND_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(CURIOUS_ARMOR.get(), CURIOUS_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(CURIOUS_ARMOR_HELMET.get(), CURIOUS_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(STALWART_ARMOR.get(), STALWART_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(STALWART_ARMOR_HELMET.get(), STALWART_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(FROST_ARMOR.get(), FROST_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(FROST_ARMOR_HELMET.get(), FROST_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(HEROS_ARMOR.get(), HEROS_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(HEROS_ARMOR_HELMET.get(), HEROS_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(TITANS_SHROUD.get(), TITANS_SHROUD.get().getRegistryName());
        uniqueMetalArmorMap.put(TITANS_SHROUD_HELMET.get(), TITANS_SHROUD_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(FULL_METAL_ARMOR.get(), FULL_METAL_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(FULL_METAL_ARMOR_HELMET.get(), FULL_METAL_ARMOR_HELMET.get().getRegistryName());
        uniqueMetalArmorMap.put(RENEGADE_ARMOR.get(), RENEGADE_ARMOR.get().getRegistryName());
        uniqueMetalArmorMap.put(RENEGADE_ARMOR_HELMET.get(), RENEGADE_ARMOR_HELMET.get().getRegistryName());
    }

    private static void putCommonMetalArmorsInMap() {
        commonMetalArmorMap.put(SPELUNKER_ARMOR.get(), SPELUNKER_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(SPELUNKER_ARMOR_HELMET.get(), SPELUNKER_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(SCALE_MAIL.get(), SCALE_MAIL.get().getRegistryName());
        commonMetalArmorMap.put(GUARDS_ARMOR.get(), GUARDS_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(GUARDS_ARMOR_HELMET.get(), GUARDS_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(REINFORCED_MAIL.get(), REINFORCED_MAIL.get().getRegistryName());
        commonMetalArmorMap.put(REINFORCED_MAIL_HELMET.get(), REINFORCED_MAIL_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(SNOW_ARMOR.get(), SNOW_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(SNOW_ARMOR_HELMET.get(), SNOW_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(CHAMPIONS_ARMOR.get(), CHAMPIONS_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(CHAMPIONS_ARMOR_HELMET.get(), CHAMPIONS_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(DARK_ARMOR.get(), DARK_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(DARK_ARMOR_HELMET.get(), DARK_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(PLATE_ARMOR.get(), PLATE_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(PLATE_ARMOR_HELMET.get(), PLATE_ARMOR_HELMET.get().getRegistryName());
        commonMetalArmorMap.put(MERCENARY_ARMOR.get(), MERCENARY_ARMOR.get().getRegistryName());
        commonMetalArmorMap.put(MERCENARY_ARMOR_HELMET.get(), MERCENARY_ARMOR_HELMET.get().getRegistryName());
    }

    private static void putUniqueLeatherArmorsInMap() {
        uniqueLeatherArmorMap.put(ARCHERS_ARMOR.get(), ARCHERS_ARMOR.get().getRegistryName());
        uniqueLeatherArmorMap.put(ARCHERS_ARMOR_HOOD.get(), ARCHERS_ARMOR_HOOD.get().getRegistryName());
        uniqueLeatherArmorMap.put(FROST_BITE.get(), FROST_BITE.get().getRegistryName());
        uniqueLeatherArmorMap.put(FROST_BITE_HELMET.get(), FROST_BITE_HELMET.get().getRegistryName());
        uniqueLeatherArmorMap.put(SPIDER_ARMOR.get(), SPIDER_ARMOR.get().getRegistryName());
        uniqueLeatherArmorMap.put(SPIDER_ARMOR_HOOD.get(), SPIDER_ARMOR_HOOD.get().getRegistryName());
        uniqueLeatherArmorMap.put(WITHER_ARMOR.get(), WITHER_ARMOR.get().getRegistryName());
        uniqueLeatherArmorMap.put(WITHER_ARMOR_HELMET.get(), WITHER_ARMOR_HELMET.get().getRegistryName());
        uniqueLeatherArmorMap.put(FOX_ARMOR.get(), FOX_ARMOR.get().getRegistryName());
        uniqueLeatherArmorMap.put(FOX_ARMOR_HOOD.get(), FOX_ARMOR_HOOD.get().getRegistryName());
        uniqueLeatherArmorMap.put(SHADOW_WALKER.get(), SHADOW_WALKER.get().getRegistryName());
        uniqueLeatherArmorMap.put(SHADOW_WALKER_HOOD.get(), SHADOW_WALKER_HOOD.get().getRegistryName());
        uniqueLeatherArmorMap.put(SPLENDID_ROBE.get(), SPLENDID_ROBE.get().getRegistryName());
        uniqueLeatherArmorMap.put(EMBER_ROBE.get(), EMBER_ROBE.get().getRegistryName());
        uniqueLeatherArmorMap.put(EMBER_ROBE_HAT.get(), EMBER_ROBE_HAT.get().getRegistryName());
        uniqueLeatherArmorMap.put(SOULDANCER_ROBE.get(), SOULDANCER_ROBE.get().getRegistryName());
        uniqueLeatherArmorMap.put(SOULDANCER_ROBE_HOOD.get(), SOULDANCER_ROBE_HOOD.get().getRegistryName());
    }

    private static void putCommonLeatherArmorsInMap() {
        commonLeatherArmorMap.put(HUNTERS_ARMOR.get(), HUNTERS_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(PHANTOM_ARMOR.get(), PHANTOM_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(PHANTOM_ARMOR_HELMET.get(), PHANTOM_ARMOR_HELMET.get().getRegistryName());
        commonLeatherArmorMap.put(THIEF_ARMOR.get(), THIEF_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(THIEF_ARMOR_HOOD.get(), THIEF_ARMOR_HOOD.get().getRegistryName());
        commonLeatherArmorMap.put(GRIM_ARMOR.get(), GRIM_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(GRIM_ARMOR_HELMET.get(), GRIM_ARMOR_HELMET.get().getRegistryName());
        commonLeatherArmorMap.put(WOLF_ARMOR.get(), WOLF_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(WOLF_ARMOR_HOOD.get(), WOLF_ARMOR_HOOD.get().getRegistryName());
        commonLeatherArmorMap.put(OCELOT_ARMOR.get(), OCELOT_ARMOR.get().getRegistryName());
        commonLeatherArmorMap.put(OCELOT_ARMOR_HOOD.get(), OCELOT_ARMOR_HOOD.get().getRegistryName());
        commonLeatherArmorMap.put(BATTLE_ROBE.get(), BATTLE_ROBE.get().getRegistryName());
        commonLeatherArmorMap.put(EVOCATION_ROBE.get(), EVOCATION_ROBE.get().getRegistryName());
        commonLeatherArmorMap.put(EVOCATION_ROBE_HAT.get(), EVOCATION_ROBE_HAT.get().getRegistryName());
        commonLeatherArmorMap.put(SOUL_ROBE.get(), SOUL_ROBE.get().getRegistryName());
        commonLeatherArmorMap.put(SOUL_ROBE_HOOD.get(), SOUL_ROBE_HOOD.get().getRegistryName());
    }

    private static void putCommonBowsInMap() {
        commonRangedWeaponMap.put(HUNTING_BOW.get(), HUNTING_BOW.get().getRegistryName());
        commonRangedWeaponMap.put(LONGBOW.get(), LONGBOW.get().getRegistryName());
        commonRangedWeaponMap.put(SHORTBOW.get(), SHORTBOW.get().getRegistryName());
        commonRangedWeaponMap.put(POWER_BOW.get(), POWER_BOW.get().getRegistryName());
        commonRangedWeaponMap.put(SOUL_BOW.get(), SOUL_BOW.get().getRegistryName());
        commonRangedWeaponMap.put(TRICKBOW.get(), TRICKBOW.get().getRegistryName());
        commonRangedWeaponMap.put(SNOW_BOW.get(), SNOW_BOW.get().getRegistryName());
    }

    private static void putCommonCrossbowsInMap() {
        commonRangedWeaponMap.put(EXPLODING_CROSSBOW.get(), EXPLODING_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(HEAVY_CROSSBOW.get(), HEAVY_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(RAPID_CROSSBOW.get(), RAPID_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(SCATTER_CROSSBOW.get(), SCATTER_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(SOUL_CROSSBOW.get(), SOUL_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(DUAL_CROSSBOW.get(), DUAL_CROSSBOW.get().getRegistryName());
        commonRangedWeaponMap.put(BURST_CROSSBOW.get(), BURST_CROSSBOW.get().getRegistryName());
    }

    private static void putUniqueCrossbowsInMap() {
        uniqueRangedWeaponMap.put(AUTO_CROSSBOW.get(), AUTO_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(AZURE_SEEKER.get(), AZURE_SEEKER.get().getRegistryName());
        uniqueRangedWeaponMap.put(BUTTERFLY_CROSSBOW.get(), BUTTERFLY_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(DOOM_CROSSBOW.get(), DOOM_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(FERAL_SOUL_CROSSBOW.get(), FERAL_SOUL_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(FIREBOLT_THROWER.get(), FIREBOLT_THROWER.get().getRegistryName());
        uniqueRangedWeaponMap.put(HARP_CROSSBOW.get(), HARP_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(LIGHTNING_HARP_CROSSBOW.get(), LIGHTNING_HARP_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(SLAYER_CROSSBOW.get(), SLAYER_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(THE_SLICER.get(), THE_SLICER.get().getRegistryName());
        uniqueRangedWeaponMap.put(VOIDCALLER.get(), VOIDCALLER.get().getRegistryName());
        uniqueRangedWeaponMap.put(BABY_CROSSBOW.get(), BABY_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(IMPLODING_CROSSBOW.get(), IMPLODING_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(SOUL_HUNTER_CROSSBOW.get(), SOUL_HUNTER_CROSSBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(CORRUPTED_CROSSBOW.get(), CORRUPTED_CROSSBOW.get().getRegistryName());
    }

    private static void putUniqueBowsInMap() {
        uniqueRangedWeaponMap.put(BONEBOW.get(), BONEBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(BOW_OF_LOST_SOULS.get(), BOW_OF_LOST_SOULS.get().getRegistryName());
        uniqueRangedWeaponMap.put(ELITE_POWER_BOW.get(), ELITE_POWER_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(GUARDIAN_BOW.get(), GUARDIAN_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(HUNTERS_PROMISE.get(), HUNTERS_PROMISE.get().getRegistryName());
        uniqueRangedWeaponMap.put(MASTERS_BOW.get(), MASTERS_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(MECHANICAL_SHORTBOW.get(), MECHANICAL_SHORTBOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(NOCTURNAL_BOW.get(), NOCTURNAL_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(PURPLE_STORM.get(), PURPLE_STORM.get().getRegistryName());
        uniqueRangedWeaponMap.put(RED_SNAKE.get(), RED_SNAKE.get().getRegistryName());
        uniqueRangedWeaponMap.put(SABREWING.get(), SABREWING.get().getRegistryName());
        uniqueRangedWeaponMap.put(THE_GREEN_MENACE.get(), THE_GREEN_MENACE.get().getRegistryName());
        uniqueRangedWeaponMap.put(THE_PINK_SCOUNDREL.get(), THE_PINK_SCOUNDREL.get().getRegistryName());
        uniqueRangedWeaponMap.put(TWIN_BOW.get(), TWIN_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(WINTERS_TOUCH.get(), WINTERS_TOUCH.get().getRegistryName());
        uniqueRangedWeaponMap.put(HAUNTED_BOW.get(), HAUNTED_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(ANCIENT_BOW.get(), ANCIENT_BOW.get().getRegistryName());
        uniqueRangedWeaponMap.put(LOVE_SPELL_BOW.get(), LOVE_SPELL_BOW.get().getRegistryName());
    }

    private static void putCommonMeleeWeaponsInMap() {
        commonWeaponMap.put(DOUBLE_AXE.get(), DOUBLE_AXE.get().getRegistryName());
        commonWeaponMap.put(CLAYMORE.get(), CLAYMORE.get().getRegistryName());
        commonWeaponMap.put(CUTLASS.get(), CUTLASS.get().getRegistryName());
        commonWeaponMap.put(KATANA.get(), KATANA.get().getRegistryName());
        commonWeaponMap.put(SOUL_KNIFE.get(), SOUL_KNIFE.get().getRegistryName());
        commonWeaponMap.put(GAUNTLET.get(), GAUNTLET.get().getRegistryName());
        commonWeaponMap.put(WHIP.get(), WHIP.get().getRegistryName());
        commonWeaponMap.put(SPEAR.get(), SPEAR.get().getRegistryName());
        commonWeaponMap.put(BATTLESTAFF.get(), BATTLESTAFF.get().getRegistryName());
        commonWeaponMap.put(SICKLE.get(), SICKLE.get().getRegistryName());
        commonWeaponMap.put(SOUL_SCYTHE.get(), SOUL_SCYTHE.get().getRegistryName());
        commonWeaponMap.put(MACE.get(), MACE.get().getRegistryName());
        commonWeaponMap.put(GREAT_HAMMER.get(), GREAT_HAMMER.get().getRegistryName());
        commonWeaponMap.put(GLAIVE.get(), GLAIVE.get().getRegistryName());
        commonWeaponMap.put(DAGGER.get(), DAGGER.get().getRegistryName());
        commonWeaponMap.put(RAPIER.get(), RAPIER.get().getRegistryName());
    }

    private static void putUniqueMeleeWeaponsInMap() {
        uniqueWeaponMap.put(CURSED_AXE.get(), CURSED_AXE.get().getRegistryName());
        uniqueWeaponMap.put(FIREBRAND.get(), FIREBRAND.get().getRegistryName());
        uniqueWeaponMap.put(HIGHLAND_AXE.get(), HIGHLAND_AXE.get().getRegistryName());
        uniqueWeaponMap.put(WHIRLWIND.get(), WHIRLWIND.get().getRegistryName());
        uniqueWeaponMap.put(FANG_OF_FROST.get(), FANG_OF_FROST.get().getRegistryName());
        uniqueWeaponMap.put(MOON_DAGGER.get(), MOON_DAGGER.get().getRegistryName());
        uniqueWeaponMap.put(GRAVE_BANE.get(), GRAVE_BANE.get().getRegistryName());
        uniqueWeaponMap.put(VENOM_GLAIVE.get(), VENOM_GLAIVE.get().getRegistryName());
        uniqueWeaponMap.put(HAMMER_OF_GRAVITY.get(), HAMMER_OF_GRAVITY.get().getRegistryName());
        uniqueWeaponMap.put(STORMLANDER.get(), STORMLANDER.get().getRegistryName());
        uniqueWeaponMap.put(FLAIL.get(), FLAIL.get().getRegistryName());
        uniqueWeaponMap.put(SUNS_GRACE.get(), SUNS_GRACE.get().getRegistryName());
        uniqueWeaponMap.put(FROST_SCYTHE.get(), FROST_SCYTHE.get().getRegistryName());
        uniqueWeaponMap.put(JAILORS_SCYTHE.get(), JAILORS_SCYTHE.get().getRegistryName());
        uniqueWeaponMap.put(NIGHTMARES_BITE.get(), NIGHTMARES_BITE.get().getRegistryName());
        uniqueWeaponMap.put(THE_LAST_LAUGH.get(), THE_LAST_LAUGH.get().getRegistryName());
        uniqueWeaponMap.put(FORTUNE_SPEAR.get(), FORTUNE_SPEAR.get().getRegistryName());
        uniqueWeaponMap.put(WHISPERING_SPEAR.get(), WHISPERING_SPEAR.get().getRegistryName());
        uniqueWeaponMap.put(BATTLESTAFF_OF_TERROR.get(), BATTLESTAFF_OF_TERROR.get().getRegistryName());
        uniqueWeaponMap.put(GROWING_STAFF.get(), GROWING_STAFF.get().getRegistryName());
        uniqueWeaponMap.put(VINE_WHIP.get(), VINE_WHIP.get().getRegistryName());
        uniqueWeaponMap.put(FIGHTERS_BINDING.get(), FIGHTERS_BINDING.get().getRegistryName());
        uniqueWeaponMap.put(MAULER.get(), MAULER.get().getRegistryName());
        uniqueWeaponMap.put(SOUL_FIST.get(), SOUL_FIST.get().getRegistryName());
        uniqueWeaponMap.put(BROADSWORD.get(), BROADSWORD.get().getRegistryName());
        uniqueWeaponMap.put(DANCERS_SWORD.get(), DANCERS_SWORD.get().getRegistryName());
        uniqueWeaponMap.put(DARK_KATANA.get(), DARK_KATANA.get().getRegistryName());
        uniqueWeaponMap.put(ETERNAL_KNIFE.get(), ETERNAL_KNIFE.get().getRegistryName());
        uniqueWeaponMap.put(HAWKBRAND.get(), HAWKBRAND.get().getRegistryName());
        uniqueWeaponMap.put(HEARTSTEALER.get(), HEARTSTEALER.get().getRegistryName());
        uniqueWeaponMap.put(MASTERS_KATANA.get(), MASTERS_KATANA.get().getRegistryName());
        uniqueWeaponMap.put(NAMELESS_BLADE.get(), NAMELESS_BLADE.get().getRegistryName());
        uniqueWeaponMap.put(TRUTHSEEKER.get(), TRUTHSEEKER.get().getRegistryName());
        uniqueWeaponMap.put(FREEZING_FOIL.get(), FREEZING_FOIL.get().getRegistryName());
        uniqueWeaponMap.put(BEE_STINGER.get(), BEE_STINGER.get().getRegistryName());
        uniqueWeaponMap.put(GREAT_AXEBLADE.get(), GREAT_AXEBLADE.get().getRegistryName());
        uniqueWeaponMap.put(SINISTER_SWORD.get(), SINISTER_SWORD.get().getRegistryName());
        uniqueWeaponMap.put(SHEAR_DAGGER.get(), SHEAR_DAGGER.get().getRegistryName());
    }

    private static void putArtifactsInMap() {
        artifactMap.put(BOOTS_OF_SWIFTNESS.get(), BOOTS_OF_SWIFTNESS.get().getRegistryName());
        artifactMap.put(CORRUPTED_BEACON.get(), CORRUPTED_BEACON.get().getRegistryName());
        artifactMap.put(CORRUPTED_SEEDS.get(), CORRUPTED_SEEDS.get().getRegistryName());
        artifactMap.put(DEATH_CAP_MUSHROOM.get(), DEATH_CAP_MUSHROOM.get().getRegistryName());
        artifactMap.put(FLAMING_QUIVER.get(), FLAMING_QUIVER.get().getRegistryName());
        artifactMap.put(GHOST_CLOAK.get(), GHOST_CLOAK.get().getRegistryName());
        artifactMap.put(GOLEM_KIT.get(), GOLEM_KIT.get().getRegistryName());
        artifactMap.put(GONG_OF_WEAKENING.get(), GONG_OF_WEAKENING.get().getRegistryName());
        artifactMap.put(HARVESTER.get(), HARVESTER.get().getRegistryName());
        artifactMap.put(ICE_WAND.get(), ICE_WAND.get().getRegistryName());
        artifactMap.put(IRON_HIDE_AMULET.get(), IRON_HIDE_AMULET.get().getRegistryName());
        artifactMap.put(LIGHT_FEATHER.get(), LIGHT_FEATHER.get().getRegistryName());
        artifactMap.put(LIGHTNING_ROD.get(), LIGHTNING_ROD.get().getRegistryName());
        artifactMap.put(LOVE_MEDALLION.get(), LOVE_MEDALLION.get().getRegistryName());
        artifactMap.put(SHOCK_POWDER.get(), SHOCK_POWDER.get().getRegistryName());
        artifactMap.put(SOUL_HEALER.get(), SOUL_HEALER.get().getRegistryName());
        artifactMap.put(TASTY_BONE.get(), TASTY_BONE.get().getRegistryName());
        artifactMap.put(TORMENT_QUIVER.get(), TORMENT_QUIVER.get().getRegistryName());
        artifactMap.put(TOTEM_OF_REGENERATION.get(), TOTEM_OF_REGENERATION.get().getRegistryName());
        artifactMap.put(TOTEM_OF_SHIELDING.get(), TOTEM_OF_SHIELDING.get().getRegistryName());
        artifactMap.put(TOTEM_OF_SOUL_PROTECTION.get(), TOTEM_OF_SOUL_PROTECTION.get().getRegistryName());
        artifactMap.put(WIND_HORN.get(), WIND_HORN.get().getRegistryName());
        artifactMap.put(WONDERFUL_WHEAT.get(), WONDERFUL_WHEAT.get().getRegistryName());
        artifactMap.put(BUZZY_NEST.get(), BUZZY_NEST.get().getRegistryName());
        artifactMap.put(ENCHANTED_GRASS.get(), ENCHANTED_GRASS.get().getRegistryName());
        artifactMap.put(CORRUPTED_PUMPKIN.get(), CORRUPTED_PUMPKIN.get().getRegistryName());
    }
}
